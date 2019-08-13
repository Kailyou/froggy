package javagame;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Tree extends movingObject
{
	
	float currentWayPointSave;

	public Tree(String direction, 
				Vector2f startPosition,
				Vector2f endPosition,
				float currentWayPoint,
				float speed) 
	{
		super(direction, startPosition, endPosition, currentWayPoint, speed);
				
		mO_startingPosition = startPosition;
		mO_currentWorldPosition = mO_startingPosition.copy();
		mO_headingVector = endPosition.copy();
		mO_headingVector.sub(startPosition);
		mO_totalDistance = startPosition.distance(endPosition); //ACKY
		mO_headingVector.normalise();
		mO_currentWayPoint = currentWayPoint;
		this.currentWayPointSave = currentWayPoint;
		mO_speed = speed;
	}

	
	
	
	//GETTER
	@Override
	public Shape getBoundingShape() 
	{
		return mO_boundingRectangle;
	}

	
	public float getX() 
	{
		return mO_currentWorldPosition.getX();
	}

	
	public float getY()
	{
		return mO_currentWorldPosition.getY();
	}
	
	
	
	
	//OTHERS
	public void resetTreeIngame()
	{
		mO_currentWorldPosition = mO_startingPosition.copy();
		mO_currentWayPoint = 0.0f;
	}
	
	
	public void resetTreeGameover()
	{
		mO_currentWorldPosition = mO_startingPosition.copy();
		mO_currentWayPoint = currentWayPointSave;
	}

	
	
	
	//INIT
	public void Initialize1() throws SlickException 
	{
		mO_objectImage = new Image("data/tree/baumstamm.png");
		mO_boundingRectangle = new Rectangle(
				mO_startingPosition.getX(),
				mO_startingPosition.getY(),
				mO_objectImage.getWidth(),
				mO_objectImage.getHeight()
				);
	}
	
	// für größere boundingbox, damit froggy nicht direkt stirbt
	public void Initialize2() throws SlickException 
	{
		mO_objectImage = new Image("data/tree/baumstamm.png");
		mO_boundingRectangle = new Rectangle(
				mO_startingPosition.getX(),
				mO_startingPosition.getY()-8,
				mO_objectImage.getWidth(),
				mO_objectImage.getHeight()+13
				);
	}

	
	
	
	//UPDATE
	public void Update1(int delta) 
	{
		mO_currentWayPoint += mO_speed * delta / 1000.0f;

		Vector2f localDelta = mO_headingVector.copy();
		localDelta.scale(mO_currentWayPoint);
		mO_currentWorldPosition = mO_startingPosition.copy();
		mO_currentWorldPosition.add(localDelta);
				
		if (mO_currentWayPoint > mO_totalDistance)
		{
			resetTreeIngame();
		}
				
		mO_boundingRectangle.setLocation(mO_currentWorldPosition);
	}
	
	// für die zweite bounding box
	public void Update2(int delta) 
	{
		mO_currentWayPoint += mO_speed * delta / 1000.0f;

		Vector2f localDelta = mO_headingVector.copy();
		localDelta.scale(mO_currentWayPoint);
		mO_currentWorldPosition = mO_startingPosition.copy();
		mO_currentWorldPosition.add(localDelta);
				
		if (mO_currentWayPoint > mO_totalDistance)
		{
			resetTreeIngame();
		}
				
		mO_boundingRectangle.setLocation(mO_currentWorldPosition.getX(), mO_currentWorldPosition.getY()-8);
	}

	
	
	
	//RENDER
	@Override
	public void Render(Graphics g) 
	{
		mO_objectImage.draw(mO_currentWorldPosition.getX(), mO_currentWorldPosition.getY());
	}
	
	

}
