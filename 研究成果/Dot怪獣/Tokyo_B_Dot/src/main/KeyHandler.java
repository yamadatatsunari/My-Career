package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener { //キーボードの入力を処理するためのカスタムクラス
	GamePanel gp;

	public boolean upPressed, downPressed, leftPressed, rightPressed,
	enterPressed, shiftPressed, shotKeyPressed; //各方向のキー（上、下、左、右）が押されているかどうか
	
	//*デバッグ機能
	public int debug = 0;
	public int debugS = 0;
	public int debugOn = 0;
	public boolean Debug1, Debug2, Debug3, Debug4;
	
	public KeyHandler(GamePanel gp){
	    this.gp = gp;
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		//特定のキー（VK_W、VK_S、VK_A、VK_D）が押された場合、対応するフラグを true に設定
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = true;
		}
		
		if(code == KeyEvent.VK_A) {
			leftPressed = true;
		}

		if(code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		
		if(code == KeyEvent.VK_ENTER && gp.gameState == gp.playState) {

			enterPressed = true;
		}
		
		if(code == KeyEvent.VK_E) {
            if(gp.gameState == gp.playState){
                gp.playSE(5);
                gp.gameState = gp.optionState;
                gp.ui.commandNum = 0;
            }
            else if(gp.gameState == gp.optionState){
                gp.playSE(6);
                gp.gameState = gp.playState;
                gp.ui.commandNum = 0;
            }
        }
		
		if(code == KeyEvent.VK_SPACE) {

			shotKeyPressed = true;
		}
		
		if(gp.gameState == gp.titleState) {
			if(code == KeyEvent.VK_W && gp.gameState == gp.titleState) {
				gp.ui.commandNum--;
				if(gp.ui.commandNum < 0) {
					gp.ui.commandNum = 2;
				}
			}
			if(code == KeyEvent.VK_S && gp.gameState == gp.titleState) {
				gp.ui.commandNum++;
				if(gp.ui.commandNum > 2) {
					gp.ui.commandNum = 0;
				}
			}
			
			if(code == KeyEvent.VK_ENTER && gp.gameState == gp.titleState) {
				if(gp.ui.commandNum == 0) {
			    	gp.playSE(5);
					//最初から
					gp.reset = true;		    	
				    gp.gameState = gp.playState;
		            gp.stopMusic();
			    	gp.playMusic(0);

				}
				if(gp.ui.commandNum == 1) {
					gp.playSE(5);
					//オプション
				    gp.gameState = gp.titleoptionState;
				    gp.ui.commandNum = 0;
				}
				if(gp.ui.commandNum == 2) {
					gp.playSE(5);
					//ゲーム終了
				    System.exit(0);
				}
			}
		}
		
		//オプション
		
		if(code == KeyEvent.VK_W && gp.gameState == gp.titleoptionState) {
			gp.playSE(7);
			gp.ui.commandNum--;
			if(gp.ui.commandNum < 0) {
				gp.ui.commandNum = 3;
			}
		}
		if(code == KeyEvent.VK_S && gp.gameState == gp.titleoptionState) {
			gp.playSE(7);
			gp.ui.commandNum++;
			if(gp.ui.commandNum > 3) {
				gp.ui.commandNum = 0;
			}
		}
		
		if(code == KeyEvent.VK_ENTER && gp.gameState == gp.titleoptionState) {
			gp.playSE(5);
			if(gp.ui.commandNum == 3) {
				//TEXT 0番目
				gp.gameState = gp.titleState;
			    gp.ui.commandNum = 1;
			}
			if(gp.ui.commandNum == 2) {
				//ゲーム終了
			    System.exit(0);
			}
		}
		
		//BGM音量調整
		if(gp.gameState == gp.titleoptionState) {
			if(code == KeyEvent.VK_A) {
				if(gp.ui.commandNum == 0 && gp.sound.volumeScale > 0) {
					gp.sound.volumeScale--;
					gp.sound.checkVolume();
					gp.playSE(5);
				} else {if(gp.ui.commandNum == 1 && gp.se.volumeScale > 0) {
					gp.se.volumeScale--;
					//gp.sound.checkVolume();
					gp.playSE(5);
				}}
			}
			if(code == KeyEvent.VK_D) {
				if(gp.ui.commandNum == 0 && gp.sound.volumeScale < 100) {
					gp.sound.volumeScale++;
					gp.sound.checkVolume();
					gp.playSE(5);
				} else if(gp.ui.commandNum == 1 && gp.se.volumeScale < 100) {
					gp.se.volumeScale++;
					//gp.sound.checkVolume();
					gp.playSE(5);
				}
			}
			//TEXT 0番目
		}
						
		//*ダッシュ
		if(code == KeyEvent.VK_SHIFT) {
			shiftPressed = true;
		}
		//*/
		
		if(code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = true;
		}
		
		if(code == KeyEvent.VK_A) {
			leftPressed = true;
		}

		if(code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if(code == KeyEvent.VK_P) {
		    if(gp.gameState == gp.playState){
		        gp.gameState = gp.pauseState;
		    }
		    else if(gp.gameState == gp.pauseState){
		        gp.gameState = gp.playState;
		    }
		}
		
		
		if(code == KeyEvent.VK_W && gp.gameState == gp.optionState) {
			gp.playSE(7);
			gp.ui.commandNum--;
			if(gp.ui.commandNum < 0) {
				gp.ui.commandNum = 3;
			}
		}
		if(code == KeyEvent.VK_S && gp.gameState == gp.optionState) {
			gp.playSE(7);
			gp.ui.commandNum++;
			if(gp.ui.commandNum > 3) {
				gp.ui.commandNum = 0;
			}
		}
		
		if(code == KeyEvent.VK_ENTER && gp.gameState == gp.optionState) {
			gp.playSE(5);
			if(gp.ui.commandNum == 3) {
				//TEXT 0番目
				gp.gameState = gp.playState;
				gp.ui.commandNum = 0;
			}
			if(gp.ui.commandNum == 2) {
				//ゲーム終了
			    System.exit(0);
			}
		}
		
		//BGM音量調整
		if(gp.gameState == gp.optionState) {
			if(code == KeyEvent.VK_A) {
				if(gp.ui.commandNum == 0 && gp.sound.volumeScale > 0) {
					gp.sound.volumeScale--;
					gp.sound.checkVolume();
					gp.playSE(5);
				} else {if(gp.ui.commandNum == 1 && gp.se.volumeScale > 0) {
					gp.se.volumeScale--;
					//gp.sound.checkVolume();
					gp.playSE(5);
				}}
			}
			if(code == KeyEvent.VK_D) {
				if(gp.ui.commandNum == 0 && gp.sound.volumeScale < 100) {
					gp.sound.volumeScale++;
					gp.sound.checkVolume();
					gp.playSE(5);
				} else if(gp.ui.commandNum == 1 && gp.se.volumeScale < 100) {
					gp.se.volumeScale++;
					//gp.sound.checkVolume();
					gp.playSE(5);
				}
			}
			//TEXT 0番目
		}
				
		// Gameover
		if(code == KeyEvent.VK_W && gp.gameState == gp.gameOverState) {
			gp.playSE(7);
			gp.ui.commandNum--;
			if(gp.ui.commandNum < 0) {
				gp.ui.commandNum = 1;
			}
		}
		if(code == KeyEvent.VK_S && gp.gameState == gp.gameOverState) {
			gp.playSE(7);
			gp.ui.commandNum++;
			if(gp.ui.commandNum > 1) {
				gp.ui.commandNum = 0;
			}
		}
		
		if(code == KeyEvent.VK_ENTER && gp.gameState == gp.gameOverState) {
			gp.playSE(5);
			if(gp.ui.commandNum == 0) {
				//TEXT 0番目
				gp.gameState = gp.playState;
				gp.ui.commandNum = 0;
				gp.reset = true;
				gp.retry();
			    gp.stopMusic();
			    gp.playMusic(0);
			}
			
			
			if(gp.ui.commandNum == 1) {
				//ゲーム終了
			    gp.gameState = gp.titleState;
			    gp.ui.commandNum = 0;
				gp.reset = true;
			    gp.stopMusic();
			    gp.playMusic(11);
			    gp.restart();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		//特定のキーが離された場合、対応するフラグを false に設定
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = false;
		}
		
		if(code == KeyEvent.VK_A) {
			leftPressed = false;
		}

		if(code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		
		if(code == KeyEvent.VK_SHIFT) {
			shiftPressed = false;
		}
		if(code == KeyEvent.VK_SPACE) {

			shotKeyPressed = false;
		}
	}
}
