package entity;

import main.GamePanel;

public class Projectile extends Entity {
	
	Entity user; // プロジェクタイルを発射したエンティティ

	public Projectile(GamePanel gp) {
		super(gp);
	}
	
	public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
		// プロジェクタイルのプロパティを設定
		this.worldX = worldX;
		this.worldY = worldY;
		this.direction = direction;
		this.alive = alive;
		this.user = user;
		this.life = this.maxLife;
	}

	public void update() {
		if (user == gp.player) { // プロジェクタイルをプレイヤーが発射した場合
			int monsterIndex1 = gp.cChecker.checkEntity(this, gp.monster1);
			int monsterIndex2 = gp.cChecker.checkEntity(this, gp.monster2);
			int monsterIndex4 = gp.cChecker.checkEntity(this, gp.monster4);

			if (monsterIndex1 != 999) {
				gp.player.damageMonster1(monsterIndex1, attack); // モンスター1にダメージを与える

				alive = false; // プロジェクタイルを消滅させる
			}
			if (monsterIndex2 != 999) {
				gp.player.damageMonster2(monsterIndex2, attack); // モンスター2にダメージを与える

				alive = false; // プロジェクタイルを消滅させる
			}

			if (monsterIndex4 != 999) {
				gp.player.damageMonster4(monsterIndex4, attack); // モンスター4にダメージを与える

				alive = false; // プロジェクタイルを消滅させる
			}
		}
		if (user != gp.player) { // プロジェクタイルをプレイヤー以外が発射した場合
			// ここにプレイヤー以外のエンティティに対する処理を追加できます
		}
		
		switch (direction) {
		case "up": worldY -= speed; break;
		case "down": worldY += speed; break;
		case "left": worldX -= speed; break;
		case "right": worldX += speed; break;
		}
		
		life--;
		if (life <= 0) {
			alive = false; // プロジェクタイルの寿命が切れたら消滅させる
		}
		
		spriteCounter++;
		if (spriteCounter > 12) {
			if (spriteNum == 1) {
				spriteNum = 2;
			} else if (spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
	}
}
