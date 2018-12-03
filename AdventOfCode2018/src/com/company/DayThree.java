package com.company;

import java.util.List;

public class DayThree {

    private int[][] getFabricMatrixWithClaims(List<String> claims) {
        int[][] fabricMatrixWithClaims = new int[1000][1000];

        for (String claim : claims) {
            int[] claimParts = getClaimParts(claim);
            int claimId = claimParts[0];
            int locationLeft = claimParts[1];
            int locationTop = claimParts[2];
            int width = claimParts[3];
            int height = claimParts[4];

            for (int i = locationLeft; i < locationLeft + width; i++) {
                for (int j = locationTop; j < locationTop + height; j++) {
                    if (fabricMatrixWithClaims[i][j] == 0) {
                        fabricMatrixWithClaims[i][j] = claimId;
                    } else {
                        fabricMatrixWithClaims[i][j] = Integer.MAX_VALUE;
                    }
                }
            }
        }
        return fabricMatrixWithClaims;
    }

    public int getAreaOfOverlappingFabric(List<String> claims) {

        int[][] fabricMatrixWithClaims = getFabricMatrixWithClaims(claims);

        int area = 0;
        for (int i = 0; i < fabricMatrixWithClaims.length; i++) {
            for (int j = 0; j < fabricMatrixWithClaims[i].length; j++) {
                if (fabricMatrixWithClaims[i][j] == Integer.MAX_VALUE) {
                    area++;
                }
            }
        }
        return area;
    }

    public int getIdOfOnlyNonOverlappingClaim(List<String> claims) {
        int[][] fabricMatrixWithClaims = getFabricMatrixWithClaims(claims);

        for (String claim : claims) {
            int[] claimParts = getClaimParts(claim);
            int claimId = claimParts[0];
            int locationLeft = claimParts[1];
            int locationTop = claimParts[2];
            int width = claimParts[3];
            int height = claimParts[4];

            int claimIdCount = 0;

            for (int i = 0; i < fabricMatrixWithClaims.length; i++) {
                for (int j = 0; j < fabricMatrixWithClaims[i].length; j++) {
                    if (fabricMatrixWithClaims[i][j] == claimId) {
                        claimIdCount++;
                    }
                }
            }
            if (claimIdCount == width * height) {
                return claimId;
            }
        }
        return -1;
    }

    private int[] getClaimParts(String claim) {
        int[] claimParts = new int[5];

        String[] parts = claim.split("@");
        int claimId = Integer.parseInt(parts[0].substring(1, parts[0].indexOf(' ')));

        String[] parts2 = parts[1].split(":");
        String location = parts2[0];
        String size = parts2[1];

        int locationLeft = Integer.parseInt(location.substring(1, location.indexOf(',')));
        int locationTop = Integer.parseInt(location.substring(location.indexOf(',') + 1));

        int width = Integer.parseInt(size.substring(1, size.indexOf('x')));
        int height = Integer.parseInt(size.substring(size.indexOf('x') + 1));

        claimParts[0] = claimId;
        claimParts[1] = locationLeft;
        claimParts[2] = locationTop;
        claimParts[3] = width;
        claimParts[4] = height;

        return claimParts;
    }
}
