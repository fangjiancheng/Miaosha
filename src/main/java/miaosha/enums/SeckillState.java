package miaosha.enums;

/**
 * 使用枚举方式表示常用字段
 * Created by fjc on 16-12-8.
 */
public enum SeckillState {
    SUCCESS(1,"秒杀成功"),
    END(0,"秒杀结束"),
    REAPEAT_KILL(-1,"重复秒杀"),
    INNER_ERROR(-2,"系统异常"),
    DATE_REWRITE(-3,"数据篡改");

    private int state;
    private String stateInfo;

    SeckillState(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }
    public static SeckillState stateOf(int intdex){
        for(SeckillState state:values()){
            if(state.getState()==intdex){
                return state;
            }
        }
        return null;
    }
}
