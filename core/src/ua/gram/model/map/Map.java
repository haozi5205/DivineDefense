package ua.gram.model.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * NOTE: https://cdn.tutsplus.com/gamedev/authors/daniel-schuller/jrpg-using-tilemap-layers.png
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class Map {

    private Spawn spawn;
    private Base base;
    private TiledMap tiledMap;
    private TiledMapTileLayer layer;
    private Path path;

    public Map(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
        layer = (TiledMapTileLayer) tiledMap.getLayers().get("Terrain");
        spawn = findSpawnPoint();
        path = normalizePath(spawn.getPosition());
        Gdx.app.log("INFO", "Map is OK");
    }

    /**
     * Parses whole map grid, looking for 'spawn' property of the tile.
     * If found one, new Spawn object is created and search is aborted.
     */
    private Spawn findSpawnPoint() {
        Spawn spawnPoint = null;
        MapProperties properties;
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                properties = layer.getCell(x, y).getTile().getProperties();
                if (properties.containsKey("spawn")) {
                    spawnPoint = new Spawn(new Vector2(x, y));
                    break;
                }
            }
        }
        return spawnPoint;
    }

    public ArrayList<Vector2> getDirectionsFrom(Vector2 start) {
        ArrayList<Vector2> route = path.getPath();
        int currentIndex = route.indexOf(start);
        List<Vector2> list = path.getDirections().subList(currentIndex + 1, route.size());
        return new ArrayList<Vector2>(list);
    }

    /**
     * Converts path to array of directions, which Actor should turn to.
     * Searches the map for 'walkable' property, starting from 'spawn' tile.
     * If found one and it is not the previous, it is added to array.
     * if the added one contains 'base' property, new Base object is
     * created and search is aborted.
     *
     * NOTE: Kind of A* path finding.
     *
     * NOTE: It is necessary to look through the map twice, because you have to make
     * sure that you start scanning from 'spawn' and finish in 'base' to avoid random
     * 'walkable' tile adding to the path.
     */
    public Path normalizePath(Vector2 start) {
        Path route = new Path();
        MapProperties properties;
        Vector2 lastDir = new Vector2();
        Vector2 position = new Vector2((int) start.x, (int) start.y);
        boolean isFound = false;
        while (!isFound) {
            for (Vector2 direction : route.DIRECTIONS) {
                if (!direction.equals(lastDir)) {
                    if ((direction.equals(Path.WEST) && position.x > 0)
                            || (direction.equals(Path.SOUTH) && position.y > 0)
                            || (direction.equals(Path.EAST) && position.x < layer.getWidth() - 1)
                            || (direction.equals(Path.NORTH) && position.y < layer.getHeight() - 1)) {
                        int currentX = (int) (position.x + direction.x);
                        int currentY = (int) (position.y + direction.y);
                        properties = layer.getCell(currentX, currentY)
                                .getTile().getProperties();
                        if (properties.containsKey("walkable")) {
                            route.addDirection(direction);
                            route.addPath(new Vector2(currentX, currentY));
                            position.add(direction);
                            lastDir = new Vector2((int) -direction.x, (int) -direction.y);
                            if (properties.containsKey("base")) {
                                base = new Base(position);
                                isFound = true;
                            }
                        }
                    }
                }
            }
        }
        Gdx.app.log("INFO", "Path is OK");
        return route;
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public TiledMapTileLayer getLayer() {
        return layer;
    }

    public Spawn getSpawn() {
        return spawn;
    }

    public Base getBase() {
        return base;
    }

    public Path getPath() {
        return path;
    }

    public ArrayList<Vector2> getDirectionsArray() {
        return path.getDirections();
    }

    public ArrayList<Vector2> getPathArray() {
        return path.getPath();
    }
}
