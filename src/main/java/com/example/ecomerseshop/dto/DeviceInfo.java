package com.example.ecomerseshop.dto;


import com.example.ecomerseshop.entity.DeviceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeviceInfo {

    String deviceId;

    DeviceType deviceType;
}
