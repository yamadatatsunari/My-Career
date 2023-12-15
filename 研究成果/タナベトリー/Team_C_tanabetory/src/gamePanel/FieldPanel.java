package gamePanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import enemy.BossBullet;
import enemy.BossEnemy;
import enemy.Enemy;
import enemy.EnemyBullet;
import enemy.Gage;
import enemy.TanabeReaper;
import player.HP;
import player.PR_Megane;
import player.Player;
import player.PlayerBullet;
import tanabetory.Main;
import tanabetory.ResultPanel;
import tanabetory.ScreenMode;
import tanabetory.Sound;

public class FieldPanel extends JPanel implements Runnable {
	//定数フィールド
	private static final long serialVersionUID = 1L;
	Color backgroundColor = Color.orange;
	//コンポーネント
	JLabel gameLabel;
	Player player;
	PlayerBullet pbullet[] = new PlayerBullet[100];
	Item item;
	T_Cola t_cola;
	Trap[] trap = new Trap[9];
	Scroll[] scroll = new Scroll[7];
	HP hp;
	Enemy enemy;
	EnemyBullet ebullet[] = new EnemyBullet[15];
	Animation[] aim = new Animation[5];
	Effect[] eff = new Effect[6];
	Score scoreManager;
	PR_Megane pr_Megane;
	BossEnemy boss;
	BossBullet []b_bullet = new BossBullet[3];
	TanabeReaper tanabeReaper;
	ResultPanel resultPanel;
	Sound sound;
	Gage gage;
	
	boolean debug_flag = false;				//デバッグ用フラグ
	JPanel redPanel = new JPanel();			//当たり判定デバッグ用_プレイヤー
	JPanel bluePanel[] = new JPanel[9]; 	//当たり判定デバッグ用_トラップ
	JPanel greenPanel[] = new JPanel[3]; 	//当たり判定デバッグ用_アイテム
	JPanel purplePanel[] = new JPanel[6]; 	//当たり判定デバッグ用_敵の弾
	JPanel yellowPanel[] = new JPanel[100];	//当たり判定デバッグ用_プレイヤーの弾
	JPanel orangePanel[] = new JPanel[15];	//当たり判定デバッグ用_エネミー
	JPanel pinkPanel = new JPanel();		//当たり判定デバッグ用_死神
	JPanel whitePanel[] = new JPanel[2];	//当たり判定デバッグ用_コーラ
	
	public boolean m_end = false;
	boolean m_finish = false;

//	JPanel panel;
	private Thread th = null;
	boolean gameloop = false;
	int flag = 0;///デバッグ用
	int flag1 = 0;//デバッグ用
	int keyFlag = 0;
	int haikeicount = 0;
	boolean ok = false;   // シーン遷移完了
	//リスナー
	MyKeyListener myKeyListener;
//	JLabel poseLabel;
	public FieldPanel panel;
	GamePanel game_Panel;
	
	//コンストラクタ
	public FieldPanel(GamePanel gamePanel) {
		//サイズは自動調整される
		game_Panel = gamePanel;
		if(debug_flag) {
			System.out.println("FieldPanel.FieldPanel()");
		}
		this.setBackground(backgroundColor);
		this.setLayout(null);
	}
	//コンストラクタが呼ばれた後手動で呼び出す
	public void prepareComponents() {
		//コンポーネント
		th = new Thread(this);
		th.start();
		
		//プレイヤー
		player = new Player(game_Panel);
		this.add(player);
		
/*************************************************************************************************/

//当たり判定確認用
		redPanel.setBackground(new Color(255, 0, 0, 127));
		redPanel.setBounds(2000, 50, 200, 200); // x座標、y座標、幅、高さを指定
		this.add(redPanel);
		for(int i=0; i<9; i++) {
			bluePanel[i] = new JPanel();
			bluePanel[i].setBackground(new Color(0, 0, 255, 127));
			bluePanel[i].setBounds(2000, 50, 200, 200); // x座標、y座標、幅、高さを指定
			this.add(bluePanel[i]);
		}
		for(int i=0; i<3; i++) {
			greenPanel[i] = new JPanel();
			greenPanel[i].setBackground(new Color(0, 255, 0, 127));
			greenPanel[i].setBounds(2000, 50, 200, 200);
			this.add(greenPanel[i]);
		}
		for(int i=0; i<6; i++) {
			purplePanel[i] = new JPanel();
			purplePanel[i].setBackground(new Color(168, 88, 168, 127));
			purplePanel[i].setBounds(2000, 50, 200, 200);
			this.add(purplePanel[i]);
		}
		for(int i=0; i<100; i++) {
			yellowPanel[i] = new JPanel();
			yellowPanel[i].setBackground(new Color(255, 255, 102, 127));
			yellowPanel[i].setBounds(2000, 0, 200, 200);
			this.add(yellowPanel[i]);
		}
		for(int i=0; i<15; i++) {
			orangePanel[i] = new JPanel();
			orangePanel[i].setBackground(new Color(255, 182, 46, 127));
			orangePanel[i].setBounds(2000, 0, 200, 200);
			this.add(orangePanel[i]);
		}
		pinkPanel.setBackground(new Color(252, 15, 192, 127));
		pinkPanel.setBounds(2000, 50, 200, 200);
		this.add(pinkPanel);
		for(int i=0; i<2; i++) {
			whitePanel[i] = new JPanel();
			whitePanel[i].setBackground(new Color(255, 182, 46, 127));
			whitePanel[i].setBounds(2000, 0, 200, 200);
			this.add(whitePanel[i]);
		}
		//当たり判定用パネルをデバッグフラグがtrueなら可視／falseなら不可視に設定
		redPanel.setVisible(debug_flag);
		for(int i=0; i<9; i++) {
			bluePanel[i].setVisible(debug_flag);
		}
		for(int i=0; i<3; i++) {
			greenPanel[i].setVisible(debug_flag);
		}
		for(int i=0; i<6; i++) {
			purplePanel[i].setVisible(debug_flag);
		}
		for(int i=0; i<100; i++) {
			yellowPanel[i].setVisible(debug_flag);
		}
		for(int i=0; i<15; i++) {
			orangePanel[i].setVisible(debug_flag);
		}
		pinkPanel.setVisible(debug_flag);
		for(int i=0; i<2; i++) {
			whitePanel[i].setVisible(debug_flag);
		}
		
/*************************************************************************************************/
		
		// UI
		// カーテン
		eff[0] = new Effect();   // カーテン(開)
		eff[0].SetAnimation("images/pipo-curtain1.png", 0, 0, Main.mainWindow.WIDTH, Main.mainWindow.HEIGHT, 0, 10, 8, 1);
		this.add(eff[0]);
		eff[1] = new Effect();   // カーテン(閉)
		eff[1].SetAnimation("images/pipo-curtain2.png", 0, 0, Main.mainWindow.WIDTH, Main.mainWindow.HEIGHT, 0, 18, 8, 1);
		this.add(eff[1]);
		eff[2] = new Effect();   // 衝突
		eff[2].SetAnimation("images/pipo-btleffect003.png", 0, 0, 100, 100, 5, 0, 10, 10);
		this.add(eff[2]);
		// HP
		hp = new HP();
		hp.SetLife(this, 0, 0, 0, 60, 60);
		this.add(hp);
		// boss HP
		gage = new Gage();
		gage.SetGage(200, 35, "images/green.png", "images/red.png", this);
		this.add(gage);
		
		// score
		scoreManager = new Score(1600, 10, 500, 50);
		scoreManager.m_label.setFont(new Font("SansSerif", Font.PLAIN, 36));
		this.add(scoreManager.m_label);
		
		//メガネの残数を表示
		pr_Megane = new PR_Megane(player.Get_X(), player.Get_Y(), 500, 50);
		pr_Megane.m_label.setFont(new Font("SansSerif", Font.PLAIN, 36));
		this.add(pr_Megane.m_label);
		
		// object
		// playerAnimation
		aim[0] = new Animation();   // run
		aim[0].SetAnimation("images/runrunrunTanabe.png", 0, 0, 200, 200, 12, 0, 10);
		this.add(aim[0]);
		aim[1] = new Animation();   // 足跡
		aim[1].SetAnimation("images/pipo-sweetseffect001_192.png", 0, 0, 75, 75, 5, 3, 1);
		this.add(aim[1]);
		eff[3] = new Effect();      // レーン移動↑(アニーメーション)
		eff[3].SetAnimation("images/LaneMoveTanabe.png", 0, 0, 200, 200, 2, 0, 50, 1);
		this.add(eff[3]);
		eff[4] = new Effect();      // 攻撃(アニーメーション)
		eff[4].SetAnimation("images/ThrowGrassTanabe.png", 0, 0, 200, 200, 2, 0, 10, 1);
		this.add(eff[4]);
		eff[5] = new Effect();      // ダメージ(アニーメーション)
		eff[5].SetAnimation("images/DamageTanabe-removebg-preview.png", 0, 0, 200, 200, 0, 0, 50, 1);
		this.add(eff[5]);
		aim[2] = new Animation();   // レーン移動(エフェクト)
		aim[2].SetAnimation("images/wing_320.png", 0, 0, 200, 200, 5, 5, 2);
		aim[2].m_use = true;
		this.add(aim[2]);
		aim[3] = new Animation();  // 無敵時間エフェクト
		aim[3].SetAnimation("images/pipo-btleffect112d.png", 0, 0, 200, 200, 5, 3, 6);
		this.add(aim[3]);
		// enemyAnimation
		aim[4] = new Animation();  // bossアニメーション
		aim[4].SetAnimation("images/boss_animation.png", 0, 0, 300, 300, 9, 0, 15);
		aim[4].m_use = false;
		this.add(aim[4]);
		
		//死神代行_田邊
		tanabeReaper = new TanabeReaper();
		this.add(tanabeReaper);
		
		// アイテム
		item = new Item();
		item.SetItem(this, "Images/grass.png", 100, 100);
		this.add(item);
		
		t_cola = new T_Cola();
		t_cola.SetCola(this, "Images/coola2.png", 100, 100);
		this.add(t_cola);
		
		// enemy
		enemy = new Enemy();
		enemy.SetEnemy(this, "images/magic_gobrin.png", 100, 100);
		this.add(enemy);
		// boss
		boss = new BossEnemy(game_Panel);
		boss.SetBossEnemy(this, "images/tanabetory.png", "images/handue.png", "images/handsita.png", 200, 200, 200, 200);
		this.add(boss);
		//ボスが使う弾
		for(int i = 0; i < 3; i++)
		{
			b_bullet[i] = new BossBullet();
			b_bullet[i].Set_bX(2000);
			this.add(b_bullet[i]);
		}
		//敵が撃つ弾
		for(int j = 0; j < 15; j++) {
			ebullet[j] = new EnemyBullet();
			ebullet[j].Set_bX(2000);
			
			this.add(ebullet[j]);
		}
		// player弾
		for(int i = 0; i < 100; i++)
		{
			pbullet[i] = new PlayerBullet();
			pbullet[i].Set_X(-100);
			this.add(pbullet[i]);
		}
		//トラップ
		int count = 0;
		for(int i = 0; i < 9; i++)
		{
			count++;
			trap[i] = new Trap();
			trap[i].Set_X(1920 + (1000 * i));
			trap[i].Set_set(count);
			this.add(trap[i]);
			if(count >= 3) {
				count = 0;
			}
		}
		
		// 地面
		for(int i=4; i<7; i++)
		{
			scroll[i] = new Scroll();
			scroll[i].SetTexture("images/ground.png", 0 , 900-((i-4)*300), 1920, 50, this, true);
			scroll[i].SetVec(13);
			this.add(scroll[i]);
		}

		// 背景
		scroll[0] = new Scroll();
		scroll[0].SetTexture("images/asa.png", 0 , 0, Main.mainWindow.WIDTH, Main.mainWindow.HEIGHT, this, true);
		scroll[0].SetVec(3);
		this.add(scroll[0]);
		scroll[1] = new Scroll();
		scroll[1].SetTexture("images/sora_tuti.png", 0 , 0, Main.mainWindow.WIDTH, Main.mainWindow.HEIGHT, this, false);
		scroll[1].SetVec(3);
		this.add(scroll[1]);
		scroll[2] = new Scroll();
		scroll[2].SetTexture("images/yuu.png", 0 , 0, Main.mainWindow.WIDTH, Main.mainWindow.HEIGHT, this, false);
		scroll[2].SetVec(3);
		this.add(scroll[2]);
		scroll[3] = new Scroll();
		scroll[3].SetTexture("images/yoru.png", 0 , 0, Main.mainWindow.WIDTH, Main.mainWindow.HEIGHT, this, false);
		scroll[3].SetVec(3);
		this.add(scroll[3]);

		myKeyListener = new MyKeyListener(this);
	}
	
	void Init() {
		if(debug_flag) {
			System.out.println("FieldPanel.Init()");
		}
		player.Init();
		scoreManager.Init();
		hp.SetStartLife();
		
		enemy.Init();
		boss.Init();
		for(int i = 0;i < 3; i++)
		{
			b_bullet[i].Init();
		}
		tanabeReaper.Init();
		
		eff[0].Reset();
		eff[0].m_use.set(0,true);
		eff[1].m_enduse = true;
		
		for(int i = 0;i < 3; i++)
		{
			item.Init(i);
		}
		int count = 0;
		for(int i = 0; i < 9; i++)
		{
			count++;
			trap[i].Set_X(1920 + (1000 * i));
			trap[i].Set_set(count);
			if(count >= 3) {
				count = 0;
			}
		}
		
		for(int j = 0; j < 15; j++) {
			ebullet[j].Set_bX(2000);
			ebullet[j].Init();
		}
	}
	
	int GetScore() {
		return scoreManager.getScore();
	}
	
	void setResultPanel() {
		this.resultPanel = game_Panel.main_Window.Get_resultPanel();
	}
	
	private class MyKeyListener implements KeyListener {
//		FieldPanel panel;
		
		//コンストラクタ
		MyKeyListener(FieldPanel p)
		{
			super();
			panel = p;
			p.addKeyListener(this);
		}
		
		@Override
		public void keyTyped(KeyEvent e) {
			//
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			//
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			//
		}
		
	}
	
	//ゲームループの開始メソッド
	public synchronized void startGameLoop()
	{
		if ( th == null ) 
		{
			th = new Thread(this);
			th.start();
		}
//		gameloop = true;
	}
	//ゲームループのコンティニューメソッド
	public synchronized void continueGameLoop() {
		if( th == null) {
			th.start();
		}
	}
	//ゲームループの終了メソッド
	public synchronized void stopGameLoop()
	{
		if ( th != null ) 
		{
			th = null;
		}
	}
	
	public void run()
	{
		if(debug_flag) {
			System.out.println("FieldPanel.run()");
		}
	//ゲームループ（定期的に再描画を実行）
		while(true){
			//System.out.println("flag, "+flag);
			if(th != null) {
				if(gameloop) {
					if(keyFlag%1==0) {
						game_Panel.multiKeyPressListener.performAction();
					}
					
					// < Animation関係 >
					// カーテン開閉
					if(m_end == true && eff[1].m_enduse == true)
					{
						eff[1].m_use.set(0, true);
						eff[1].Update();
					}
					
					// animation,effectのupdate
					eff[0].Update();   // カーテン
					eff[2].Update();   // 衝突時エフェクト
					eff[3].Update();   // palyer:レーン移動
					eff[3].SetEffectPos(player.x, player.y+30, 0);			//レーン移動の位置設定
					eff[4].Update();   // palyer:攻撃
					eff[4].SetEffectPos(player.x, player.y+24, 0);			//攻撃モーションの位置設定
					eff[5].Update();   // palyer:ダメージ
					eff[5].SetEffectPos(player.x, player.y+10, 0);			//ダメージモーションの位置設定
					aim[0].Update(player.x, player.y-0);   // player:run
					aim[1].Update(player.x + 25, player.y + 150);   // player:足元
					aim[2].Update(player.x, player.y);   // 羽
					aim[3].Update(player.x, player.y-0);   // 無敵時間
					aim[4].Update(boss.m_x[0] + 100, boss.m_y[0] - 100);   // boss
					
					// 無敵
					if(player.Get_count() > 0)
					{
						aim[3].m_use = true;
					}
					else
					{
						aim[3].m_use = false;
					}
					
					// レーン移動エフェクト
					if(eff[3].m_use.get(0) == true)
					{
						aim[2].m_use = true;
					}
					else
					{
						aim[2].m_use = false;
					}
					
					// アニーメーション戻す
					if(eff[3].m_use.get(0) == false && eff[4].m_use.get(0) == false && eff[5].m_use.get(0) == false)
					{
						aim[0].m_use = true;
						aim[1].m_use = true;
					}
					
					// < Object関係 >
					// player
					player.setLocation(player.getX(),player.getY());
					player.Update();
					if(player.Get_hp() <= 0)
					{
						gameloop = false;
						setResultPanel();
						resultPanel.setScore(GetScore(), "images/result_tanabe.png", 800, 720);
						
						game_Panel.sound.stopBGM();					// ゲームBGMの停止
						game_Panel.sound.resetBGMPosition();		// 再生位置のリセット
						
						Main.mainWindow.setFrontScreenAndFocus(ScreenMode.RESULT);
					}
					if(boss.Get_use(0) == false)
					{
						gameloop = false;
						setResultPanel();
						scoreManager.increaseScore(5000);
						resultPanel.setScore(GetScore(), "images/ending.png", 320, 600);
						
						game_Panel.sound.stopBGM();					// ゲームBGMの停止
						game_Panel.sound.resetBGMPosition();		// 再生位置のリセット
						
						Main.mainWindow.setFrontScreenAndFocus(ScreenMode.RESULT);
					}
					//System.out.println("player.Get_X:"+player.Get_X()+", player.Get_Y:"+player.Get_Y());
					
					// enemy
					enemy.EnemyUpdate(1);
					// BossEnemy
					if(enemy.m_wave == 7)
					{
						boss.Update(1);
						aim[4].m_use = true; 
						if(boss.Get_hp(0) <= 0)
						{
							aim[4].m_use = false; 
						}
					}
					gage.SetPos(boss.m_x[0] + 190, boss.m_y[0] + 200);
					gage.Update(boss.m_hp[0]);
					
					//Playerの弾とenemyの判定
					for(int i = 0; i < 100; i++)
					{
						if(player.Get_megane() == true && pbullet[i].Get_flag() == false)
						{
							PlayerAnimationManager(eff[4].m_use, 0);
							pbullet[i].Set_Bullet(player.Get_X(),player.Get_Y() + 70);
							pbullet[i].Set_flag(true);
							player.Set_megane(false);
						}
						pbullet[i].Update();
						pbullet[i].setLocation(pbullet[i].Get_X(),pbullet[i].Get_Y());

						for(int j = 0; j < 15; j++)
						{
							if(collision(pbullet[i].Get_X()+10, pbullet[i].Get_Y()+18, enemy.Get_X(j), enemy.Get_Y(j), 79, 32, 100, 100) == 1) 
							{
								if(pbullet[i].Get_flag() == true)
								{
									if(enemy.Get_Use(j)) {
										if(enemy.Get_X(j) < 1900)
										{//enemyに対する処理
											for(int a=0; a<10; a++)
											{
												if(eff[2].m_use.get(a) == false)
												{
													eff[2].m_use.set(a, true);
													eff[2].SetEffectPos(enemy.Get_X(j), enemy.Get_Y(j), a);
													break;
												}
											}
											game_Panel.sound.playSE(4);
											scoreManager.increaseScore(50);
											enemy.Set_hp(j,-1);
											pbullet[i].Set_flag(false);
										}
									}
								}
							}
						}
						
						//プレイヤーの弾とボスの判定
						for(int j = 0; j < 3; j++)
						{
							if(collision(pbullet[j].Get_X()+10, pbullet[j].Get_Y()+18, boss.Get_X(j)+120, boss.Get_Y(j)-90, 79, 32, 250, 300) == 1) 
							{
								if(boss.Get_use(j) == true && boss.Get_X(j) < 1900)
								{
									if(pbullet[i].Get_flag() == true)
									{
										for(int a=0; a<10; a++)
										{
											if(eff[2].m_use.get(a) == false)
											{
												eff[2].m_use.set(a, true);
												eff[2].SetEffectPos(boss.Get_X(j), boss.Get_Y(j), a);
												break;
											}
										}
										if(j == 1)
										{
											boss.Set_atcount(j,0);
										}
										
										if(j == 2)
										{
											boss.Set_atcount(j,0);
										}
										boss.Set_hp(j,-1);
										game_Panel.sound.playSE(4);
										scoreManager.increaseScore(50);
										pbullet[i].Set_flag(false);
									}
								}
							}
						}
					}
					
					// item
					item.ItemUpdate(-5);
					//アイテムとプレイヤーの当たり判定
					for(int i = 0; i < 3; i++)
					{
						if(collision(player.Get_X() + 75, player.Get_Y() + 120, item.Get_X(i)+12, item.Get_Y(i)+18, 50, 80, 79, 32) == 1 && item.Get_item_true(i) == true) 
						{
							game_Panel.sound.playSE(5);
							
							player.Set_zansu(1);
							item.Set_item_true(i,false);
							item.Set_count(i, 300);
						}
					}
					
					t_cola.Update(-5);
					for(int i = 0; i < t_cola.COLAMAX; i++)
					{
						if(collision(player.Get_X() + 75, player.Get_Y() + 120, t_cola.Get_X(i)+30, t_cola.Get_Y(i), 50, 80, 40, 100) == 1 && t_cola.Get_cola_true(i) == true) 
						{
							game_Panel.sound.playSE(5);
							if(player.Get_hp() < 3)
							{
								player.Set_hp(1);
								hp.m_labelPlsArray[player.Get_hp() - 1].setVisible(true);
							}

							t_cola.Set_cola_true(i,false);
							t_cola.Set_count(i, 300);
						}
					}
					
					//trap用Update
					for(int i = 0; i < 9; i++)
					{
						trap[i].setLocation(trap[i].Get_X(), trap[i].Get_Y());
						trap[i].Update();
						
						if(collision(player.Get_X() + 75, player.Get_Y() + 120, trap[i].Get_X(), trap[i].Get_Y(), 50, 80, 80, 80) == 1) {
							if(player.Get_count() <= 0)
							{
								game_Panel.sound.playSE(3);
								game_Panel.sound.playSE(6);
								
								for(int a=0; a<10; a++)
								{
									if(eff[2].m_use.get(a) == false)
									{
										eff[2].m_use.set(a, true);
										eff[2].SetEffectPos(trap[i].getX(), trap[i].getY(), a);
										break;
									}
								}
								PlayerAnimationManager(eff[5].m_use, 0);
								player.Set_hp(-1);
								player.Set_count(120);
								trap[i].Set_X(2500);
							}
						}
					}
					
					//死神代行_田邊
					tanabeReaper.Update(player.Get_X(), player.Get_Y());
					if(collision(player.Get_X() + 75, player.Get_Y() + 120, tanabeReaper.Get_X(), tanabeReaper.Get_Y(), 50, 80, 150, 150) == 1) {
						if(player.Get_count() <= 0)
						{
							game_Panel.sound.playSE(3);
							game_Panel.sound.playSE(6);

							PlayerAnimationManager(eff[5].m_use, 0);
							player.Set_hp(-3);
							player.Set_count(120);
						}
					}
					
					// 背景、地面
					for(int i = 0; i < 7; i++)
					{
						scroll[i].actionPerformed(null);
					}
					// 背景切り替え
					if(flag1 == 0)
					{
						haikeicount = 0;
						scroll[haikeicount].m_use = true;
					}
					else if(flag1 % 1800 == 0)
					{
						if(haikeicount < 3)
						{
							haikeicount++;
							scroll[haikeicount].m_use = true;
							scroll[haikeicount -1].m_use = false;
						}
						else
						{
							haikeicount = 0;
							scroll[haikeicount].m_use = true;
							scroll[haikeicount + 3].m_use = false;
						}
						
					}
					
					//敵の弾
					for(int j = 0; j < 15; j++) {
						if(enemy.Get_aflag(j) == true)
						{
							game_Panel.sound.playSE(2);
							ebullet[j].Set_Bullet(enemy.Get_X(j), enemy.Get_Y(j));
							enemy.Set_aflag(j, false);
							ebullet[j].Set_flag(true);
						}
						ebullet[j].setLocation(ebullet[j].Get_bX(), ebullet[j].Get_bY());
						ebullet[j].Update();
						if(collision(player.Get_X()+70, player.Get_Y() + 50, ebullet[j].Get_bX(), ebullet[j].Get_bY()+10, 50, 150, 50, 30) == 1)
						{//敵の弾との当たり判定
							if(player.Get_count() <= 0)
							{
								game_Panel.sound.playSE(3);
								game_Panel.sound.playSE(6);
								
								PlayerAnimationManager(eff[5].m_use, 0);
								player.Set_hp(-1);
								player.Set_count(120);
								ebullet[j].Set_flag(false);
							}
						}
					}
					
					//ボスの弾
					for(int i = 0; i < 3; i++)
					{
						if(boss.Get_attack(i) == true)
						{
							b_bullet[i].Set_Bullet(boss.Get_X(i), boss.Get_Y(i));
							if(game_Panel.Get_debugFlag()) {
								System.out.println("BOSS" + i);
							}
							boss.Set_attack(i, false);
							boss.Set_atcount(i, -600);
							b_bullet[i].Set_flag(true);
						}
						b_bullet[i].setLocation(b_bullet[i].Get_bX(), b_bullet[i].Get_bY());
						b_bullet[i].Update(player.Get_X() + 70,player.Get_Y() + 50);
						
						if(collision(player.Get_X()+70, player.Get_Y() + 50, b_bullet[i].Get_bX(), b_bullet[i].Get_bY()+10, 50, 150, 50, 30) == 1)
						{//敵の弾との当たり判定
							if(player.Get_count() <= 0)
							{
								game_Panel.sound.playSE(3);
								game_Panel.sound.playSE(6);
								
								PlayerAnimationManager(eff[5].m_use, 0);
								player.Set_hp(-1);
								player.Set_count(120);
								b_bullet[i].Set_flag(false);
							}
						}
					}
					
					hp.updatePlayerLife(player.Get_hp());
					
					if(keyFlag % 12 == 0) {
						scoreManager.increaseScore(-1);
					}
					if(debug_flag) {
						System.out.println(player.Get_hp());
						System.out.println("life" + hp.m_nowLife);
					}
					
					pr_Megane.Update(player.Get_zansu(), player.Get_X(), player.Get_Y());			//メガネの残数の表示位置と数をアップデート
/*************************************************************************************************/
					//当たり判定確認用
					if(debug_flag) {
						redPanel.setBounds(player.Get_X()+75, player.Get_Y()+120, 50, 80);			//プレイヤー当たり判定
						for(int i=0; i<9; i++) {
							bluePanel[i].setBounds(trap[i].Get_X(), trap[i].Get_Y(), 80, 80);		//トラップ当たり判定
						}
						for(int i=0; i<3; i++) {
							greenPanel[i].setBounds(item.Get_X(i)+12, item.Get_Y(i)+18, 79, 32);			//アイテム（左向きメガネ）当たり判定
						}
						for(int i=0; i<6; i++) {
							purplePanel[i].setBounds(ebullet[i].Get_bX(), ebullet[i].Get_bY()+10, 50, 30);			//敵の弾の当たり判定
						}
						for(int i=0; i<100; i++) {
							yellowPanel[i].setBounds(pbullet[i].Get_X()+10, pbullet[i].Get_Y()+18, 79, 32);			//自分の弾（右向きメガネ）の当たり判定
						}
						for(int i=0; i<15; i++) {
							orangePanel[i].setBounds(enemy.Get_X(i), enemy.Get_Y(i), 100, 100);			//エネミーの当たり判定
						}
						pinkPanel.setBounds(tanabeReaper.Get_X(), tanabeReaper.Get_Y(), 150, 150);			//プレイヤー当たり判定
						for(int i=0; i<2; i++) {
							whitePanel[i].setBounds(t_cola.Get_X(i)+30, t_cola.Get_Y(i), 40, 100);			//コーラの当たり判定
						}
						if(player.Get_hp() <= 0) {							//プレイヤーの体力が無くなったら
							player.Set_debug_hp();							//体力を回復させ、
							for(int i=0; i<3; i++) {
								hp.m_labelPlsArray[i].setVisible(true);		//HPのハートを回復させる
							}
						}
					}
/*************************************************************************************************/
					repaint();
					flag1++;
					
					if(eff[1].m_count_y.get(0) == 17)
					{
						m_finish = true;
						//eff[1].m_count_y.set(0, 0);
					}
					if(debug_flag) {
						System.out.println("eff1 " + eff[1].m_use.get(0));
						System.out.println("eff0 " + eff[0].m_use.get(0));
					}
					
				}
				try
				{
					
//					ResultPanel resultPanel = game_Panel.main_Window.Get_resultPanel();
//					resultPanel.repaint();
					Thread.sleep((int)(1000.0/60.0));
	//				System.out.println();
					
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			flag++;
			keyFlag++;
			if(keyFlag > 7200000) {
				keyFlag = 0;
			}
			
			// カーテン閉めた後、シーン遷移
			if(m_finish == true)
			{
				Init();
				if(debug_flag) {
					System.out.println("GamePanle.fieldPanelStopGameLoop()");
				}
				flag1 = 0;
				Main.mainWindow.setFrontScreenAndFocus(ScreenMode.TITLE);
				ok = true;
				m_finish = false;
			}
			if(ok == true)
			{
				gameloop = false;
				m_finish = false;
				eff[1].m_use.set(0, false);
				eff[1].m_count_y.set(0, 0);
				eff[1].m_enduse = false;
				eff[1].Reset();
				m_end = false;
				ok = false;
			}
		}
	}
	
	//当たり判定
		private int collision(int xp, int yp, int tx, int ty, int pw, int ph, int tw, int th){	//xpはplayerのx座標、txはtrapのx座標、pwはplayerの画像幅、twはtrapの画像幅
			int answer = 0;
			if((xp + pw) >= tx && xp <= tx + tw) {
				if(yp <= (ty + th) && (yp + ph) >= ty) {
					answer = 1;
//					System.out.println(answer);
//					homeLabel = new JLabel("←click this button or press 'h' to home");
//					homeLabel.setBounds(100, 5, 250, 30);
//					homeLabel.setBorder(BorderFactory.createEtchedBorder(3, Color.black, Color.white));
				}
			}else {
				answer = 0;
			}
			return answer;
			
		}
		
	// Playerのアニーメーション切り替え管理
	public void PlayerAnimationManager(List<Boolean> ttrue, int t1)
	{
		if(aim[0].m_use == true)
		{
			ttrue.set(t1, true);
			aim[0].m_use = false;
			aim[1].m_use = false;
		}
	}
}
