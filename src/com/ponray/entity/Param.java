package com.ponray.entity;

/**
 *参数
 */
public class Param {

    private Long ID;

    private Standard standard;

    private String name;

    private int type;

    private String unit;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Standard getStandard() {
        return standard;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    @Override
    public String toString() {
        return "Param{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", type=" + ParamType.getName(this.type) +
                ", unit='" + unit + '\'' +
                '}';
    }

    /**
     * 枚举 参数类型
     */
    enum ParamType{
        USERPARAM("用户参数",0),resultParam("结果参数",1);

        private String name;

        private int index;

        private ParamType(String name,int index){
            this.name = name;
            this.index = index;
        }

        public static String getName(int index) {
            for (ParamType c : ParamType.values()) {
                if (c.getIndex() == index) {
                    return c.name;
                }
            }
            return null;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}
