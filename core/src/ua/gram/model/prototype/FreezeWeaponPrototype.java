package ua.gram.model.prototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class FreezeWeaponPrototype extends WeaponPrototype {
    public String region;
    public float delay;
    public int frames;

    @Override
    public float getDuration() {
        return duration == 0 ? delay * frames : duration;
    }
}