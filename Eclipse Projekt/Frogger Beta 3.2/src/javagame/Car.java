package javagame;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Car extends movingObject {
	float currentWayPointSave;

	// CONSTRUCTOR
	public Car(String direction, Vector2f startPosition, Vector2f endPosition,
			   float currentWayPoint, float speed) {
		super(direction, startPosition, endPosition, currentWayPoint, speed);

		mO_direction = direction;
		mO_startingPosition = startPosition;
		mO_currentWorldPosition = mO_startingPosition.copy();
		mO_headingVector = endPosition.copy();
		mO_headingVector.sub(startPosition);
		mO_totalDistance = startPosition.distance(endPosition);
		mO_headingVector.normalise();
		mO_currentWayPoint = currentWayPoint;
		this.currentWayPointSave = currentWayPoint;
		mO_speed = speed;
	}

	
	// GETTER
	@Override
	public Shape getBoundingShape() {
		return mO_boundingRectangle;
	}

	
	// OTHERS
	public void resetCarIngame() {
		mO_currentWorldPosition = mO_startingPosition.copy();
		mO_currentWayPoint = 0.0f;
	}

	public void resetCarGameover() {
		mO_currentWorldPosition = mO_startingPosition.copy();
		mO_currentWayPoint = currentWayPointSave;
	}

	
	public void Initialize() throws SlickException {

		if (mO_direction == "Left_1") {
			mO_objectImage = new Image("data/car/car_01.jpg");
			mO_boundingRectangle = new Rectangle(mO_startingPosition.getX(),
					mO_startingPosition.getY(), mO_objectImage.getWidth(),
					mO_objectImage.getHeight());
		}

		if (mO_direction == "Left_2") {
			mO_objectImage = new Image("data/car/car_03.png");
			mO_boundingRectangle = new Rectangle(mO_startingPosition.getX(),
					mO_startingPosition.getY(), mO_objectImage.getWidth(),
					mO_objectImage.getHeight());
		}

		if (mO_direction == "Right_1") {
			mO_objectImage = new Image("data/car/car_02.png");
			mO_boundingRectangle = new Rectangle(mO_startingPosition.getX(),
					mO_startingPosition.getY(), mO_objectImage.getWidth(),
					mO_objectImage.getHeight());
		}

		if (mO_direction == "Right_2") {
			mO_objectImage = new Image("data/car/car_04.png");
			mO_boundingRectangle = new Rectangle(mO_startingPosition.getX(),
					mO_startingPosition.getY(), mO_objectImage.getWidth(),
					mO_objectImage.getHeight());
		}

	}

	public void Update(int delta) {
		mO_currentWayPoint += mO_speed * delta / 1000.0f;

		Vector2f localDelta = mO_headingVector.copy();
		localDelta.scale(mO_currentWayPoint);
		mO_currentWorldPosition = mO_startingPosition.copy();
		mO_currentWorldPosition.add(localDelta);

		if (mO_currentWayPoint > mO_totalDistance) {
			resetCarIngame();
		}

		mO_boundingRectangle.setLocation(mO_currentWorldPosition);
	}

	@Override
	public void Render(Graphics g) {
		mO_objectImage.draw(mO_currentWorldPosition.getX(),
				mO_currentWorldPosition.getY());
	}
}
