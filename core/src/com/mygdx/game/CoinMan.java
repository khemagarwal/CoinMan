package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;


import java.util.ArrayList;
import java.util.Random;

public class CoinMan extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background,background1,background2,background3,background4,background5;
	Texture[] man;
	Texture dizzy;
	int manState=0;
	int pause=0;
	float gravity = 0.4f;
	float velocity= 0;
	int manY=0;
	Rectangle manRectangle;
	Random random;
	BitmapFont font;

	ArrayList<Integer>coinXs=new ArrayList<Integer>();
	ArrayList<Integer>coinYs=new ArrayList<Integer>();
	ArrayList<Rectangle> coinRectangles=new ArrayList<Rectangle>();

	Texture coin;
	int coinCount;
	int gameState=0;

	ArrayList<Integer>bombXs=new ArrayList<Integer>();
	ArrayList<Integer>bombYs=new ArrayList<Integer>();
	ArrayList<Rectangle> bombRectangles=new ArrayList<Rectangle>();
	Texture bomb;
	int bombCount;

	int score=0;

	int x1=0,y1=0,a1=0,b1=0;
	int x3=0,y3=0,a3=0,b3=0;
	int x2=0,y2=0,a2=0,b2=0;


	private Rectangle textureRegionBounds1;
	private Rectangle textureRegionBounds2;
	private int speed = 100;

	@Override
	public void create () {
		batch = new SpriteBatch();
		background=new Texture("morningaboveV1.jpg");
		background1=new Texture("morningaboveV2.jpg");

		background2=new Texture("morningmiddleV1.jpg");
		background3=new Texture("morningmiddleV2.jpg");

		background4=new Texture("morninglowV1.jpg");
		background5=new Texture("morninglowV1.jpg");



		man=new Texture[4];
		man[0]=new Texture("frame-1.png");
		man[1]=new Texture("frame-2.png");
		man[2]=new Texture("frame-3.png");
		man[3]=new Texture("frame-4.png");
		manY=Gdx.graphics.getHeight()/2;
		coin=new Texture("coin.png");
		bomb=new Texture("bomb.png");
		random=new Random();
		font=new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(10);
		dizzy=new Texture("dizzy-1.png");
		a3=Gdx.graphics.getWidth();
		a1=Gdx.graphics.getWidth();
		a2=Gdx.graphics.getWidth();


	}

	public void makeCoin()
	{
		float height=random.nextFloat() * Gdx.graphics.getHeight();
		coinYs.add((int)height);
		coinXs.add(Gdx.graphics.getWidth());

	}
	public void makeBomb()
	{
		float height=random.nextFloat() * Gdx.graphics.getHeight();
		bombYs.add((int)height);
		bombXs.add(Gdx.graphics.getWidth());

	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background,x1,Gdx.graphics.getHeight()-background.getHeight(),Gdx.graphics.getWidth(),background.getHeight());
		batch.draw(background1,a1,Gdx.graphics.getHeight()-background.getHeight(),Gdx.graphics.getWidth(),background.getHeight());

		batch.draw(background2,x2,background4.getHeight(),Gdx.graphics.getWidth(),Gdx.graphics.getHeight()-background.getHeight()-background4.getHeight());
		batch.draw(background3,a2,background4.getHeight(),Gdx.graphics.getWidth(),Gdx.graphics.getHeight()-background.getHeight()-background4.getHeight());


		batch.draw(background4,x3,0,Gdx.graphics.getWidth(),background4.getHeight());
		batch.draw(background5,a3,0,Gdx.graphics.getWidth(),background4.getHeight());





//		batch.draw(background1,a,b,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
//		batch.draw(background1,a,b,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if(gameState==1)
		{
			x1-=2;
			a1-=2;

			if(a1==0)
			{
				x1=Gdx.graphics.getWidth();

			}
			if(x1==0)
			{
				a1=Gdx.graphics.getWidth();
			}

			x2-=8;
			a2-=8;

			if(a2==0)
			{
				x2=Gdx.graphics.getWidth();

			}
			if(x2==0)
			{
				a2=Gdx.graphics.getWidth();
			}

			x3-=15;
			a3-=15;

			if(a3==0)
			{
				x3=Gdx.graphics.getWidth();

			}
			if(x3==0)
			{
				a3=Gdx.graphics.getWidth();
			}


			//game is live
			//BOMBS
			if(bombCount<200){
				bombCount++;
			}
			else
			{
				bombCount=0;
				makeBomb();
			}
			bombRectangles.clear();
			for (int i=0;i<bombXs.size();i++)
			{
				batch.draw(bomb,bombXs.get(i),bombYs.get(i));
				bombXs.set(i,bombXs.get(i)-16);
				bombRectangles.add(new Rectangle(bombXs.get(i),bombYs.get(i),bomb.getWidth(),bomb.getHeight()));

			}




			//COINS
			if(coinCount<100){
				coinCount++;
			}
			else
			{
				coinCount=0;
				makeCoin();
			}

			coinRectangles.clear();
			for (int i=0;i<coinXs.size();i++)
			{
				batch.draw(coin,coinXs.get(i),coinYs.get(i));
				coinXs.set(i,coinXs.get(i)-8);
				coinRectangles.add(new Rectangle(coinXs.get(i),coinYs.get(i),coin.getWidth(),coin.getHeight()));
			}


			if(Gdx.input.justTouched()){
				velocity=-15;
			}
			if(pause < 4)
			{
				pause++;
			}
			else
			{
				pause=0;
				if(manState < 3)
				{
					manState++;
				}
				else
				{
					manState = 0;
				}
			}

			velocity=velocity+gravity;
			manY -= velocity;
			if(manY <= background4.getHeight()-70)
			{
				manY=background4.getHeight()-70;
			}

		}
		else if(gameState == 0)
		{
			//waiting to start
			if(Gdx.input.justTouched())
			{
				gameState=1;
			}

		}
		else if(gameState == 2)
		{
			//game over
			if(Gdx.input.justTouched())
			{
				gameState=1;
				manY=Gdx.graphics.getHeight()/2;
				score=0;
				velocity=0;
				coinYs.clear();
				coinXs.clear();
				coinRectangles.clear();
				coinCount=0;

				bombYs.clear();
				bombXs.clear();
				bombRectangles.clear();
				bombCount=0;
			}


		}



		if(gameState == 2) {
			batch.draw(dizzy, Gdx.graphics.getWidth() / 2 - man[manState].getWidth() / 2, manY);

		}
		else
		{
			batch.draw(man[manState], Gdx.graphics.getWidth() / 2 - man[manState].getWidth() / 2, manY);

		}
		manRectangle= new Rectangle(Gdx.graphics.getWidth()/2 - man[manState].getWidth()/2,manY,man[manState].getWidth(),man[manState].getHeight());
		for (int i=0;i<coinRectangles.size();i++)
		{
			if(Intersector.overlaps(manRectangle,coinRectangles.get(i)))
			{
				score++;
				coinRectangles.remove(i);
				coinXs.remove(i);
				coinYs.remove(i);
				break;
			}
		}

		for (int i=0;i<bombRectangles.size();i++)
		{
			if(Intersector.overlaps(manRectangle,bombRectangles.get(i)))
			{
				Gdx.app.log("Bomb!!","Booooom");
				gameState=2;
			}
		}

		font.draw(batch,String.valueOf(score),100,200);
		batch.end();

	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
