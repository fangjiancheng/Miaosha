package miaosha.dto;


import miaosha.entity.SuccessKilled;
import miaosha.enums.SeckillState;

/**
 * 封装秒杀后的结果
 * <p>
 * Created by fjc on 16-12-8.
 */
public class SeckillExecution {
    private long seckillId;

    private int status;

    private String stateInfo;
    //    存储秒杀成功对象
    private SuccessKilled successKilled;

    //成功秒杀的构造器
    public SeckillExecution(long seckillId, SeckillState seckillState, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.status =seckillState.getState();
        this.stateInfo = seckillState.getStateInfo();
        this.successKilled = successKilled;
    }

    //    失败的构造器
    public SeckillExecution(long seckillId, SeckillState seckillState) {
        this.seckillId = seckillId;
        this.status =seckillState.getState();
        this.stateInfo = seckillState.getStateInfo();
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }

    @Override
    public String toString() {
        return "SeckillExecution{" +
                "seckillId=" + seckillId +
                ", status=" + status +
                ", stateInfo='" + stateInfo + '\'' +
                ", successKilled=" + successKilled +
                '}';
    }
}
