package javagame;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;


// The car is able to move left and right.
public abstract class movingObject 
{
	// The images we would like to paint the car/tree with.
	Image mO_objectImage;
	// The Strings we use to define the object
	String mO_direction;	
	// The starting position of the object.
	Vector2f mO_startingPosition;
	// from current vector to ending vector.
	Vector2f mO_headingVector;
	// The current position we have in world coordinates.
	Vector2f mO_currentWorldPosition;
	// Flags if we are going from start to end position
	float mO_currentWayPoint;
	// The distance between start and end
	float mO_totalDistance;
	float mO_speed;
	// Flags if we are going from start to end position.
	boolean mO_isGoingFromStartToEnd;
	Shape mO_boundingRectangle;
	
	
	
	
	//CONSTRUCTOR
	public movingObject(String direction,
						Vector2f startPosition, 
						Vector2f endPosition,
						float currentWayPoint,
						float speed)
	{		
		
	}
	
	
	
	
	//GETTER
	public abstract Shape getBoundingShape();
		
	
	//RENDER
	public abstract void Render(Graphics g);
		
		
}

