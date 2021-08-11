package com.company;

import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence = "";
    public static int[] heroesHealth = {260, 250, 270, 300, 500, 300, 270, 300};
    public static int[] heroesDamage = {15, 20, 10, 0, 10, 20, 15, 20};
    public static String[] heroesAttackType = {
            "Physical ", "Magical ", "Kinetic ", "Medic ", "Golem ", "Berserk ", "Lucky ", "Thor "};
    static Random random = new Random();


    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            round();
        }
    }

    public static void round() {
        if (bossHealth > 0) {
            if (thor()) {
                chooseBossDefence();
                bossHits();
                medic();
                golem();
                berserk();
                lucky();
            } else System.out.println("BOSS STUNNED!");
            heroesHit();
            printStatistics();
        }
    }

    private static boolean thor() {
        return random.nextBoolean();
    }

    private static void lucky() {
        boolean uklon = random.nextBoolean();
        if (heroesHealth[6] > 0 && uklon) {
            heroesHealth[6] += bossDamage - (bossDamage / 5);
            System.out.println("UKLON");
        }
    }

    static void medic() {
        int hp = random.nextInt(20) + 10;
        int randomHero = random.nextInt(heroesHealth.length);
        if (randomHero == 3) {
            medic();
        } else if (heroesHealth[randomHero] < 100 && heroesHealth[randomHero] > 0 && heroesHealth[3] > 0) {
            heroesHealth[randomHero] += hp;
            System.out.println("Medic healed: " + heroesAttackType[randomHero] + " for: " + hp);
        }
    }

    static void golem() {
        int takeDamage = bossDamage / 5;
        int aliveHeroes = 0;
        if (heroesHealth[4] > 0) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (i == 4) continue;
                else if (heroesHealth[i] > 0) {
                    aliveHeroes++;
                    heroesHealth[i] += takeDamage;
                }
            }
            heroesHealth[4] -= takeDamage * aliveHeroes;
            System.out.println("Golem take: " + aliveHeroes * takeDamage);
        }

    }

    static void berserk() {
        int blocked = random.nextInt(30) + 10;

        if (heroesHealth[5] > 0) {
            bossHealth -= blocked;
            heroesHealth[5] += blocked;
            System.out.println("Berserk blocked: " + blocked);
        }

    }

    public static void chooseBossDefence() {
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        if (randomIndex == 3) chooseBossDefence();
        else if (heroesHealth[randomIndex] > 0) {
            bossDefence = heroesAttackType[randomIndex];
            System.out.println("Boss choose: " + bossDefence);
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }


    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (bossDefence == heroesAttackType[i]) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    bossHealth = bossHealth - heroesDamage[i] * coeff;
                    System.out.println(
                            "Critical Damage: " + heroesDamage[i] * coeff);
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                heroesHealth[i] = heroesHealth[i] - bossDamage;
            }
        }
    }

    public static void printStatistics() {
        if (bossHealth < 0) {
            bossHealth = 0;
        }
        System.out.println("++++++++++++++");
        System.out.println("Boss health: " + bossHealth + " ["
                + bossDamage + "]\n");
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] < 0 || heroesHealth[i] == 0) {
                heroesHealth[i] = 0;
            } else {
                System.out.println(heroesAttackType[i]
                        + " health: " + heroesHealth[i] + " ["
                        + heroesDamage[i] + "]");
            }
        }
        System.out.println("++++++++++++++");
    }
}