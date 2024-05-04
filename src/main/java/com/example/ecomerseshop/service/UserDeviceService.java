package com.example.ecomerseshop.service;

import com.example.ecomerseshop.dto.DeviceInfo;
import com.example.ecomerseshop.entity.RefreshTokenEntity;
import com.example.ecomerseshop.entity.UserDeviceEntity;
import com.example.ecomerseshop.exception.TokenRefreshException;
import com.example.ecomerseshop.repository.UserDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;


@Service
public class UserDeviceService {


    private final UserDeviceRepository userDeviceRepository;

    @Autowired
    public UserDeviceService(UserDeviceRepository userDeviceRepository) {
        this.userDeviceRepository = userDeviceRepository;
    }


    public Optional<UserDeviceEntity> findDeviceByUserId(Integer userId, String deviceId) {
        return userDeviceRepository.findByUserIdAndDeviceId(userId, deviceId);
    }


    public UserDeviceEntity createUserDevice(DeviceInfo deviceInfo) {

        UserDeviceEntity userDevice = new UserDeviceEntity();

        userDevice.setDeviceId(deviceInfo.getDeviceId());
        userDevice.setDeviceType(deviceInfo.getDeviceType());
        userDevice.setIsRefreshActive(true);
        userDevice.setCreatedAt(Instant.now());
        userDevice.setUpdatedAt(Instant.now());

        return userDevice;
    }


}
