package miaosha.entity;

import java.util.Date;

/**
 * Created by fjc on 17-3-4.
 */
public class SuccessKilled {
    private long seckillIdl;

    private long usePhone;

    private short state;

    private Date createTime;

    private Seckill seckill;

    public long getSeckillIdl() {
        return seckillIdl;
    }

    public void setSeckillIdl(long seckillIdl) {
        this.seckillIdl = seckillIdl;
    }

    public long getUsePhone() {
        return usePhone;
    }

    public void setUsePhone(long usePhone) {
        this.usePhone = usePhone;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Seckill getSeckill() {
        return seckill;
    }

    public void setSeckill(Seckill seckill) {
        this.seckill = seckill;
    }

    @Override
    public String toString() {
        return "SuccessKilled{" +
                "seckillIdl=" + seckillIdl +
                ", usePhone=" + usePhone +
                ", state=" + state +
                ", createTime=" + createTime +
                '}';
    }
}
