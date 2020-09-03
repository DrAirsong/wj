package com.evan.wj.dao;

import com.evan.wj.pojo.BluetoothInfo;
import com.evan.wj.pojo.UseridAndBluetooth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface BluetoothInfoDAO extends JpaRepository<BluetoothInfo, UseridAndBluetooth> {
    List<BluetoothInfo> findAllByUserid(Integer userid);
    @Query(value = "select * from bluetoothinfo where userid =?1 and (connect_date >= ?2)",nativeQuery = true)
    public List<BluetoothInfo> getByUseridAndDate(Integer userid, String date);
}
