package com.company;

import java.util.Random;

public class WaveSpawner {

    /* klasa koja sluzi kao level design*/

    private Handler handler;
    private Random rand;

    private HUD hud;

    public static int enemyCount ;


    public WaveSpawner(Handler handler, HUD hud){
        this.handler = handler;
        this.hud = hud;

    }

    /* u ovom tick metodu se generisu waves kroz wave counter i switch petlju */

    public void tick() {


        rand = new Random();

        if (enemyCount <= 0) {
            hud.waveCount(hud.getWaveCount() + 1);
        }
        switch (hud.getWaveCount()) {
            case 1:

                if (enemyCount <= 0) {
                    for (int j = 0; j > -2000; j -= 200) {
                        handler.addObject(new EnemyLVL1(rand.nextInt(Game.WIDTH - 70),
                                j - rand.nextInt(100),
                                ID.EnemyLVL1, handler));
                        enemyCount++;

                        if (enemyCount == handler.object.size()) {
                            break;
                        }
                    }
                }

            case 2:

                if (enemyCount <= 0) {
                    for (int j = 0; j > -2000; j -= 100) {
                        handler.addObject(new EnemyLVL1(rand.nextInt(Game.WIDTH - 70),
                                j - rand.nextInt(100),
                                ID.EnemyLVL1, handler));
                        enemyCount++;
                        if (enemyCount == handler.object.size()) {
                            break;
                        }
                    }

                }
            case 3:

                if (enemyCount <= 0) {
                    for (int j = 0; j > -3000; j -= 200) {
                        handler.addObject(new EnemyLVL1(rand.nextInt(Game.WIDTH -70),
                                j - rand.nextInt(100),
                                ID.EnemyLVL1, handler));
                        enemyCount++;
                        if (enemyCount == handler.object.size()) {
                            break;
                        }
                    }

                }
            case 4:

                if (enemyCount <= 0) {
                    for (int j = 0; j > -4000; j -= 200) {
                        handler.addObject(new EnemyLVL1(rand.nextInt(Game.WIDTH -70),
                                j - rand.nextInt(100),
                                ID.EnemyLVL1, handler));
                        enemyCount++;
                        if (enemyCount == handler.object.size()) {
                            break;
                        }
                    }

                }
            case 5:

                if (enemyCount <= 0) {
                    for (int j = 0; j > -4000; j -= 100) {
                        handler.addObject(new SuicideEnemy(rand.nextInt(Game.WIDTH - 70),
                                j - rand.nextInt(100),
                                ID.SuicideEnemy, handler));
                        enemyCount++;
                        if (enemyCount == handler.object.size()) {
                            break;
                        }
                    }

                }
            case 6:

                if (enemyCount <= 0) {
                    for (int j = 0; j > -4000; j -= 200) {
                        handler.addObject(new EnemyLVL1(rand.nextInt(Game.WIDTH -70),
                                j - rand.nextInt(100),
                                ID.EnemyLVL1, handler));
                        enemyCount++;
                        if (enemyCount % 2 == 0) {

                            handler.addObject(new SuicideEnemy(rand.nextInt(Game.WIDTH -70),
                                    j - rand.nextInt(500),
                                    ID.SuicideEnemy, handler));
                            enemyCount++;
                        }

                        if (enemyCount == handler.object.size()) {
                            break;

                        }

                    }

                }

            case 7:
                if (enemyCount <= 0) {
                    for (int j = 0; j > -4000; j -= 200) {
                        handler.addObject(new EnemyLVL2(rand.nextInt(Game.WIDTH -70),
                                j - rand.nextInt(100),
                                ID.EnemyLVL2, handler));
                        enemyCount++;
                        if (enemyCount == handler.object.size()) {
                            break;
                        }
                    }

                }
            case 8:

                if (enemyCount <= 0) {
                    for (int j = 0; j > -6000; j -= 200) {
                        handler.addObject(new EnemyLVL1(rand.nextInt(Game.WIDTH -70),
                                j - rand.nextInt(200),
                                ID.EnemyLVL1, handler));
                        enemyCount++;
                        if (enemyCount % 2 == 0) {

                            handler.addObject(new SuicideEnemy(rand.nextInt(Game.WIDTH -70),
                                    j - rand.nextInt(500),
                                    ID.SuicideEnemy, handler));
                            enemyCount++;
                        }
                        if (enemyCount % 3 == 0) {

                            handler.addObject(new EnemyLVL2(rand.nextInt(Game.WIDTH -100),
                                    j - rand.nextInt(200),
                                    ID.EnemyLVL2, handler));
                            enemyCount++;
                        }

                        if (enemyCount == handler.object.size()) {
                            break;

                        }

                    }

                }

            case 9:

                if (enemyCount <= 0) {
                    for (int j = 0; j > -6000; j -= 200) {
                        handler.addObject(new EnemyLVL2(rand.nextInt(Game.WIDTH -100),
                                j - rand.nextInt(200),
                                ID.EnemyLVL2, handler));
                        enemyCount++;
                        if (enemyCount % 4 == 0) {

                            handler.addObject(new SuicideEnemy(rand.nextInt(Game.WIDTH -70),
                                    j - rand.nextInt(500),
                                    ID.SuicideEnemy, handler));
                            enemyCount++;
                        }
                        if (enemyCount % 5 == 0) {

                            handler.addObject(new EnemyLVL1(rand.nextInt(Game.WIDTH -70),
                                    j - rand.nextInt(200),
                                    ID.EnemyLVL1, handler));
                            enemyCount++;
                        }

                        if (enemyCount == handler.object.size()) {
                            break;

                        }

                    }

                }
            case 10:

                if (enemyCount <= 0) {

                        handler.addObject(new BossLVL1(0,-300,ID.Boss,handler));
                        enemyCount++;

                        if (enemyCount == handler.object.size()) {
                            break;

                        }

                    }

        }

    }

    /*public int getEnemyCount(){
        return enemyCount;
    }

    public void setEnemyCount(int enemyCount){
        this.enemyCount = enemyCount;
    }*/


}
