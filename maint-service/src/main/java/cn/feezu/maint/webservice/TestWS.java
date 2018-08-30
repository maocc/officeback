package cn.feezu.maint.webservice;

import cn.feezu.maint.webservice.vehicle.service.CarService;
import cn.feezu.maint.webservice.ws.vehicle.service.CarDTOArray;
import cn.feezu.maint.webservice.ws.vehicle.service.Result;

import java.net.MalformedURLException;

/**
 * @author maocc
 * @description
 * @date: 2018/3/30 15:37
 */
public class TestWS {
	public static void main(String[] args) {
		cn.feezu.maint.webservice.ws.vehicle.service.CarService carService1 = null;
		try {
			carService1 = new CarService("http://localhost:8080").getCarServiceImplPort();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		CarDTOArray carDTOArray = carService1.findCarInfoByLicense("京AC0302");
		if(carDTOArray.getItem() != null && carDTOArray.getItem().size() > 0){
			Result result = carService1.controlCarByManager("WS",carDTOArray.getItem().get(0).getCarId());
			System.out.println(result.isSuccess());
		}

	}
}
