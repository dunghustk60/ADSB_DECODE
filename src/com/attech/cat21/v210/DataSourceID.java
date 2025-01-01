/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat21.v210;

/**
 *
 * @author andh
 */
public class DataSourceID {
    private int sac;
    private int sic;

    /** SAC **/
    public int getSac() {
        return sac;
    }

    public void setSac(int sac) {
        this.sac = sac;
    }

    /** SIC **/
    public int getSic() {
        return sic;
    }

    public void setSic(int sic) {
        this.sic = sic;
    }
    
    @Override
    public String toString() {
        return "SAC:" + this.sac + " SIC:" + this.sic;
    }
}
