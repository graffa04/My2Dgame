package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;




import main.GamePanel;
import main.KeyHandler;


public class Player extends Entity{
	
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	 boolean bootsOn = false;
	  int bootsCounter = 0;
	

	public void seDefaultValues() {
		
		worldX = gp.tileSize * 23; 
		worldY = gp.tileSize * 21; 
		
		speed = 4; 
		this.direction = "down";
		
		// PLAYER STATUS
		maxLife = 6;
		life = maxLife;
	}
	public void getPlayerImage() {
		
		up1 = setup("/player/boy_up_1.png");
		up2 = setup("/player/boy_up_2.png");
		down1 = setup("/player/boy_down_1.png");
		down2 = setup("/player/boy_down_2.png");
		left1 = setup("/player/boy_left_1.png");
		left2 = setup("/player/boy_left_2.png");
		right1 = setup("/player/boy_right_1.png");
		right2 = setup("/player/boy_right_2.png");

	}	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		super(gp);
		
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.height = 32;
		solidArea.width = 32;
		
		seDefaultValues();
		getPlayerImage();
	}
	
	public void update()  {
		
	    if (this.keyH.upPressed || this.keyH.downPressed || 
	    	this.keyH.leftPressed || this.keyH.rightPressed) {
	    	
			if(keyH.upPressed == true) {
				this.direction = "up";
									
			}
			else if(keyH.downPressed == true) {
				this.direction = "down";
				 
			}
			else if (keyH.leftPressed == true) {
				this.direction = "left";
				
			}
			else if (keyH.rightPressed == true) {
				this.direction = "right";
				
			}
			
			//IF COLLISION IS FALSE PLAYER CAN MOVE
			if(collisionOn == false) {
				
				switch(direction) {
				case "up":	worldY -= speed; break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break; 
				}
			}
			
			spriteCounter++;
			if(spriteCounter > 10) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}
				else if(spriteNum == 2) {
					spriteNum = 1; 
				}
				spriteCounter = 0;
			}
			//CHECK TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			//CHECK NPC COLLISION
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
			
			//CHECK EVENT 
			gp.eHandler.checkEvent();
			
			gp.keyH.enterPressed = false;
			
			// CHECK OBJECT COLLISION 
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
		}		
	}
			
			
	public void pickUpObject(int i) {
		
		if(i != 999) {
			

		}
	}
	
	public void interactNPC(int i) {
		
		if(i != 999) {
			
			if(gp.keyH.enterPressed == true) {
				gp.gameState = gp.dialogueState;
				gp.npc[i].speak();
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		switch(direction) {
		case "up":
			if(spriteNum == 1) {
				image = up1;
			}
			if(spriteNum == 2) {
				image = up2;
			}
			break;
		case "down":
			if(spriteNum == 1) {
				image = down1;
			}
			if(spriteNum == 2) {
				image = down2;
			}
			break;
		case "left":
			if(spriteNum == 1) {
				image = left1;
			}
			if(spriteNum == 2) {
				image = left2;
			}
			break;
		case "right":
			if(spriteNum == 1) {
				image = right1;
			}
			if(spriteNum == 2) {
				image = right2;
			}
			break;
		}
		g2.drawImage(image, screenX, screenY, null);
	}
}
