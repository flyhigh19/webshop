package com.bianyiit.dao.Impl;

import com.bianyiit.dao.OrderDao;
import com.bianyiit.domain.*;
import com.bianyiit.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class OrderDaoImpl implements OrderDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int findGoodsDid(String goodsID, String item) {
        String sql="select did from goods where goodsID=? and item=?";
        return jdbcTemplate.queryForObject(sql,Integer.class,goodsID,item);
    }

    @Override
    public GoodsDetail findGoodsDetail(int goodsDid) {
        String sql="select * from goods_detail where did=?";
        try {
            return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(GoodsDetail.class),goodsDid);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Goods findGoods(String goodsID, String item) {
        String sql="select * from goods where goodsID=? and item=?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Goods.class),goodsID,item);
    }

    @Override
    public int addReceiveMsg(ReceiveMsg receiveMsg, Member loginUser) {
        String sql = "insert into receivemsg values (?,?,?,?,?,?,?)";
        return jdbcTemplate.update(sql,
                receiveMsg.getReceiveID(),
                receiveMsg.getMember_ReceiveAddress(),
                receiveMsg.getReceiveName(),
                receiveMsg.getReceivePhone(),
                receiveMsg.getReceiveAddress(),
                receiveMsg.getReceiveDetailAddress(),
                receiveMsg.getIsDefault()
        );
    }

    @Override
    public int getMember_ReceiveMsg(String memberID) {
        String sql="select member_ReceiveMsg from member where memberID=?";
        return jdbcTemplate.queryForObject(sql,Integer.class,memberID);
    }

    @Override
    public int updateReceiveMsg(ReceiveMsg receiveMsg) {
        String sql="update receivemsg set member_ReceiveAddress=?,receiveName=?,receivePhone=?,receiveAddress=?,receiveDetailAddress=?,isDefault=? where receiveID=?";
        try {
            return jdbcTemplate.update(sql,receiveMsg.getMember_ReceiveAddress(),receiveMsg.getReceiveName(),receiveMsg.getReceivePhone(),receiveMsg.getReceiveAddress(),receiveMsg.getReceiveDetailAddress(),receiveMsg.getIsDefault(),receiveMsg.getReceiveID());
        } catch (DataAccessException e) {
            return 0;
        }
    }

    @Override
    public int deleteReceiveMsg(int receiveID) {
        String sql="delete from receivemsg where receiveID=?";
        try {
            return jdbcTemplate.update(sql,receiveID);
        } catch (DataAccessException e) {
            return 0;
        }
    }

    @Override
    public ReceiveMsg findReceiveMsgIsDefault(int receiveID) {
        String sql="select * from receivemsg where receiveID=?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(ReceiveMsg.class),receiveID);
    }

    @Override
    public List<ReceiveMsg> findReceiveMsg(int member_receiveAddress) {
        String sql="select * from receivemsg where member_receiveAddress=?";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(ReceiveMsg.class),member_receiveAddress);
    }

    @Override
    public ReceiveMsg findReceiveMsg(String memberID) {
        String sql="select * from receivemsg where member_ReceiveAddress=(select member_ReceiveMsg from member where memberID=?) and isDefault=1";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(ReceiveMsg.class),memberID);
    }

    @Override
    public String getLastOrderID() {
        String sql="select orderID from ordermain order by orderID desc limit 1";
        try {
            return jdbcTemplate.queryForObject(sql,String.class);
        } catch (DataAccessException e) {
            //e.printStackTrace();
        }
        return null;
    }

    @Override
    public int addOrderMain(Order_Main orderMain) {
        String sql="insert into ordermain values(?,?,?,?,?,?,?,?,?,?)";
        return jdbcTemplate.update(sql,
                orderMain.getOrderID(),
                orderMain.getOrderTime(),
                orderMain.getTotalMoney(),
                orderMain.getPaymentTime(),
                orderMain.getOrderStatus(),
                orderMain.getReceiveName(),
                orderMain.getReceivePhone(),
                orderMain.getReceiveAddress(),
                orderMain.getReceiveDetailAddress(),
                orderMain.getMemberID());
    }

    @Override
    public void addOrderDetail(Order_Detail orderDetail) {
        String sql="insert into orderdetail values(?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,
                orderDetail.getOrderID(),
                orderDetail.getGoodsID(),
                orderDetail.getItem(),
                orderDetail.getPrice(),
                orderDetail.getGoodsNum(),
                orderDetail.getFactory(),
                orderDetail.getImgsrc(),
                orderDetail.getDescription(),
                orderDetail.getDiscountPrice(),
                orderDetail.getYufei());
    }

    @Override
    public void deleteShopCart(String memberID) {
        String sql="delete from mycart where memberID=?";
        jdbcTemplate.update(sql,memberID);
    }

    @Override
    public List<Order_Main> findConfirmOrderMain(String memberID) {
        String sql="select * from ordermain where memberID=?";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Order_Main.class),memberID);
    }

    @Override
    public List<Order_Detail> findConfirmOrderDetail(String orderID) {
        String sql="select * from orderdetail where orderID=?";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Order_Detail.class),orderID);
    }

    @Override
    public Order_Main findConfirmOrder(String memberID, String orderID) {
        String sql="select * from ordermain where memberID=? and orderID=?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Order_Main.class),memberID,orderID);
    }

    @Override
    public int deleteMyPayOrderDetail(String orderID) {
        String sql="delete from orderdetail where orderID in(?)";
        return jdbcTemplate.update(sql,orderID);
    }

    @Override
    public int deleteMyPayOrderMain(String orderID) {
        String sql="delete from ordermain where orderID=?";
        return jdbcTemplate.update(sql,orderID);
    }
}
