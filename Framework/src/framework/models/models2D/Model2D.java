package framework.models.models2D;

import framework.models.Model;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 26/09/13
 * Time: 12:20
 */
public interface Model2D extends Model {
    void addRotation(int degrees, float xAxis, float yAxis, float zAxis);
    void clearRotations();
}
