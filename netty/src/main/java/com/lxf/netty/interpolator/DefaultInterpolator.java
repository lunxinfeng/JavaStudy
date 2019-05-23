package com.lxf.netty.interpolator;

public class DefaultInterpolator implements Interpolator {
    @Override
    public int interpolator(int reConnectIndex, int reConnectNumMax) {
        return 1000 + 1000 * reConnectIndex * reConnectIndex;
    }
}
