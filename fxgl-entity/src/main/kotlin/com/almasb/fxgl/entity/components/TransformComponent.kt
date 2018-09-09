package com.almasb.fxgl.entity.components

import com.almasb.fxgl.core.serialization.Bundle
import com.almasb.fxgl.entity.component.Component
import com.almasb.fxgl.entity.component.CopyableComponent
import com.almasb.fxgl.entity.component.CoreComponent
import com.almasb.fxgl.entity.component.SerializableComponent
import javafx.beans.property.DoubleProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.geometry.Point2D


//data class Transform(val x: Double, val y: Double, val rotation: Double, val scaleX: Double, val scaleY: Double)

/**
 *
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
@CoreComponent
class TransformComponent(x: Double, y: Double, angle: Double, scaleX: Double, scaleY: Double) :
        Component(),
        SerializableComponent,
        CopyableComponent<TransformComponent> {

    constructor(p: Point2D) : this(p.x, p.y, 0.0, 0.0, 0.0)
    constructor() : this(0.0, 0.0, 0.0, 0.0, 0.0)

    private val propX: DoubleProperty = SimpleDoubleProperty(x)
    private val propY: DoubleProperty = SimpleDoubleProperty(y)
    private val propZ: DoubleProperty = SimpleDoubleProperty(0.0)
    private val propAngle: DoubleProperty = SimpleDoubleProperty(angle)
    private val propScaleX: DoubleProperty = SimpleDoubleProperty(scaleX)
    private val propScaleY: DoubleProperty = SimpleDoubleProperty(scaleY)

    private val propPositionOriginX = SimpleDoubleProperty(0.0)
    private val propPositionOriginY = SimpleDoubleProperty(0.0)

    private val rotationOrigin = Point2D.ZERO
    private val scaleOrigin = Point2D.ZERO

    var x: Double
        get() = propX.value
        set(value) { propX.value = value }

    var y: Double
        get() = propY.value
        set(value) { propY.value = value }

    var z: Double
        get() = propZ.value
        set(value) { propZ.value = value }

    var angle: Double
        get() = propAngle.value
        set(value) { propAngle.value = value }

    var scaleX: Double
        get() = propScaleX.value
        set(value) { propScaleX.value = value }

    var scaleY: Double
        get() = propScaleY.value
        set(value) { propScaleY.value = value }

    var position: Point2D
        get() = Point2D(x, y)
        set(value) { setPosition(value.x, value.y) }

    var positionOriginX: Double
        get() = propPositionOriginX.value
        set(value) { propPositionOriginX.value = value }

    var positionOriginY: Double
        get() = propPositionOriginY.value
        set(value) { propPositionOriginY.value = value }




    fun xProperty() = propX
    fun yProperty() = propY
    fun zProperty() = propZ

    fun scaleXProperty() = propScaleX
    fun scaleYProperty() = propScaleY

    fun angleProperty() = propAngle

    fun positionOriginXProperty() = propPositionOriginX
    fun positionOriginYProperty() = propPositionOriginY

    fun setPosition(x: Double, y: Double) {
        this.x = x
        this.y = y
    }

    /**
     * Translate X by given value.
     *
     * @param x dx
     */
    fun translateX(x: Double) {
        this.x += x
    }

    /**
     * Translate Y by given value.
     *
     * @param y dy
     */
    fun translateY(y: Double) {
        this.y += y
    }

    /**
     * Translate x and y by given values.
     *
     * @param x dx value
     * @param y dy value
     */
    fun translate(x: Double, y: Double) {
        translateX(x)
        translateY(y)
    }

    /**
     * Translate x and y by given vector.
     *
     * @param vector translate vector
     */
    fun translate(vector: Point2D) {
        translate(vector.x, vector.y)
    }

    /**
     * @param position the point to move towards
     * @param distance the distance to move
     */
    fun translateTowards(position: Point2D, distance: Double) {
        translate(position.subtract(x, y).normalize().multiply(distance))
    }

    /**
     * @param other the other component
     * @return distance in pixels from this position to the other
     */
    fun distance(other: TransformComponent): Double {
        return position.distance(other.position)
    }

    /**
     * Rotate entity view by given angle.
     * Note: this doesn't affect hit boxes. For more accurate
     * collisions use [com.almasb.fxgl.physics.PhysicsComponent].
     *
     * @param byAngle rotation angle in degrees
     */
    fun rotateBy(byAngle: Double) {
        propAngle.value += byAngle
    }

    /**
     * Set absolute rotation of the entity view to angle
     * between vector and positive X axis.
     * This is useful for projectiles (bullets, arrows, etc)
     * which rotate depending on their current velocity.
     * Note, this assumes that at 0 angle rotation the scene view is
     * facing right.
     *
     * @param vector the rotation vector / velocity vector
     */
    fun rotateToVector(vector: Point2D) {
        propAngle.value = Math.toDegrees(Math.atan2(vector.y, vector.x))
    }

    override fun toString(): String {
        return "Transform(" + ")"
    }

    override fun write(bundle: Bundle) {
        bundle.put("propX", x)
        bundle.put("propY", y)
    }

    override fun read(bundle: Bundle) {
        setPosition(bundle.get("propX"), bundle.get("propY"))
    }

    override fun copy(): TransformComponent {
        TODO()
    }
}