package com.company;

import java.awt.*;
import java.util.*;
import java.util.List;

public class DaySix {

    private List<Point> getCoordinates(List<String> input) {
        List<Point> coordinatesList = new ArrayList<>();
        for (String s : input) {
            String[] xAndY = s.split(", ");
            Point point = new Point(Integer.valueOf(xAndY[0]), Integer.valueOf(xAndY[1]));
            coordinatesList.add(point);
        }
        return coordinatesList;
    }

    private List<List<Integer>> getGridWithClosestLocations(List<Point> points) {
        List<List<Integer>> grid = getGridWithOriginalCoordinates(points);

        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(0).size(); j++) {
                if (grid.get(i).get(j) == -1) {
                    int closestPointId = getClosestPointId(i, j, points);
                    grid.get(i).set(j, closestPointId);
                }
            }
        }
        return grid;
    }

    private List<List<Integer>> getGridWithSpecialClosestRegion(List<Point> points) {
        List<List<Integer>> grid = getGridWithOriginalCoordinates(points);
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(0).size(); j++) {
                if (grid.get(i).get(j) == -1) {
                    int totalDistance = getTotalDistance(i, j, points);
                    if (totalDistance < 10000) {
                        grid.get(i).set(j, Integer.MAX_VALUE);
                    }
                }
            }
        }
        return grid;
    }

    public int getSizeofSpecialCloseRegion(List<String> input) {
        List<Point> coordinates = getCoordinates(input);
        List<List<Integer>> grid = getGridWithSpecialClosestRegion(coordinates);

        int specialCloseRegionSize = 0;

        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(0).size(); j++) {
                if (grid.get(i).get(j) == Integer.MAX_VALUE) {
                    specialCloseRegionSize++;
                } else if (grid.get(i).get(j) != -1) {
                    if (hasNeighbouringSpecialSpace(i, j, grid)) {
                        specialCloseRegionSize++;
                    }
                }
            }
        }
        return specialCloseRegionSize;
    }

    private boolean hasNeighbouringSpecialSpace(int i, int j, List<List<Integer>> grid) {
        if (j-1 >= 0 && grid.get(i).get(j-1) == Integer.MAX_VALUE) {
            return true;
        } else if (j+1 < grid.get(0).size() && grid.get(i).get(j+1) == Integer.MAX_VALUE) {
            return true;
        } else if (i-1 >= 0 && grid.get(i-1).get(j) == Integer.MAX_VALUE) {
            return true;
        } else if (i+1 < grid.size() && grid.get(i+1).get(j) == Integer.MAX_VALUE) {
            return true;
        }
        return false;
    }

    private int getTotalDistance(int x, int y, List<Point> points) {
        int totalDistance = 0;
        for (int i = 0; i < points.size(); i++) {
            int manhattanDistance = manhattanDistance(x, y, points.get(i).y, points.get(i).x);
            totalDistance += manhattanDistance;
        }
        return totalDistance;
    }

    private List<List<Integer>> getGridWithOriginalCoordinates(List<Point> points) {
        List<List<Integer>> grid = new ArrayList<>();
        int endCoordinate = getEndCoordinates(points);
        for (int i = 0; i <= endCoordinate; i++) {
            grid.add(new ArrayList<>());
            for (int j = 0; j <= endCoordinate; j++) {
                grid.get(i).add(-1);
            }
        }

        for (int i = 0; i < points.size(); i++) {
            int x = points.get(i).x;
            int y = points.get(i).y;
            grid.get(y).set(x, i);
        }
        return grid;
    }

    public int getFurthestLocationDistance(List<String> input) {
        List<Point> coordinates = getCoordinates(input);
        List<List<Integer>> grid = getGridWithClosestLocations(coordinates);

        Set<Integer> infinite = new HashSet<>();
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(0).size(); j++) {
                if (i == 0 || j == 0 || i == grid.size() - 1 || j == grid.get(0).size() - 1) {
                    infinite.add(grid.get(i).get(j));
                }
            }
        }

        int mostDistance = -1;
        for (int i = 0; i < coordinates.size(); i++) {
            if (!infinite.contains(i)) {
                int distance = 0;
                for (int k = 0; k < grid.size(); k++) {
                    for (int j = 0; j < grid.get(0).size(); j++) {
                        if (grid.get(k).get(j) == i) {
                            distance++;
                        }
                    }
                }
                mostDistance = Math.max(mostDistance, distance);
            }
        }
        return mostDistance;
    }

    private int getClosestPointId(int x, int y, List<Point> points) {
        int closestDistance = Integer.MAX_VALUE;
        int closestPointId = -1;
        for (int i = 0; i < points.size(); i++) {
            int manhattanDistance = manhattanDistance(x, y, points.get(i).y, points.get(i).x);
            if (manhattanDistance < closestDistance) {
                closestDistance = manhattanDistance;
                closestPointId = i;
            } else if (manhattanDistance == closestDistance) {
                closestPointId = -1;
            }
        }
        return closestPointId;
    }

    private int getEndCoordinates(List<Point> points) {
        int maxX = 0;
        int maxY = 0;
        for (Point point : points) {
            maxX = Math.max(maxX, point.x);
            maxY = Math.max(maxY, point.y);
        }
        return Math.max(maxX, maxY);
    }

    private int manhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x2 - x1) + Math.abs(y2 - y1);
    }
}
