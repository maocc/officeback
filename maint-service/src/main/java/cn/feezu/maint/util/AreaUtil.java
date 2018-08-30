package cn.feezu.maint.util;

import java.awt.geom.Point2D;

import com.alibaba.fastjson.JSON;

import cn.feezu.common.util.DistanceUtil;
import cn.feezu.wzc.common.map.Coords;
import cn.feezu.wzc.common.map.GeometricalPatternsUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 区域判定
 * @author zhangfx
 *
 */
public class AreaUtil {

	/**
	 * Rectangle(矩形):1 :左下维度坐下经度,右上维度右上经度</br>
	 * 圆形(Circle):2 :31.839064_117.219116,5,维度1_经度1,半径（单位：米）,…</br>
	 * Polygon(多边形):3
	 * :31.839064_117.219116,31.83253_117.219403,维度1_经度1,维度2_经度2,…</br>
	 * 
	 * @param type
	 * @param points
	 * @param lat
	 * @param lng
	 * @return
	 */
	public static boolean in(byte type, String points, String lat, String lng) {
		Point2D.Double target = new Point2D.Double( Double.parseDouble(lng),Double.parseDouble(lat));
		// Rectangle(矩形):1 :"{\"path\":[{\"lng\":116.275811,\"lat\":40.068084},{\"lng\":116.338621,\"lat\":40.068084},{\"lng\":116.338621,\"lat\":40.045884},{\"lng\":116.275811,\"lat\":40.045884}]}";
		// 圆形(Circle):2 :{\"center\":{\"lng\":116.300946,\"lat\":40.057144},\"radius\":149.304569057084},半径（单位：米）,…
		// Polygon(多边形):3
		// :31.839064_117.219116,31.83253_117.219403,维度1_经度1,维度2_经度2,…

		boolean result = false;
		switch (type) {
		
		case 2:
			Circle circle = JSON.parseObject(points,Circle.class); 
			Point2D.Double centerCircle = new Point2D.Double(circle.getCenter().getLng(),circle.getCenter().getLat());
			result = GeometricalPatternsUtil.isInCircleByRadius(centerCircle, circle.getRadius(), target);
			break;
		case 1:
			Polygon point  = JSON.parseObject(points, Polygon.class);
			Point2D.Double lowerLeft = new Point2D.Double(point.getPath()[3].getLng(),point.getPath()[3].getLat());
			Point2D.Double upperRight = new Point2D.Double(point.getPath()[1].getLng(),point.getPath()[1].getLat());

			result = GeometricalPatternsUtil.isInRectangle(lowerLeft, upperRight, target);
			if(result){
				break;
			}
		case 3:
			Coords coords = new Coords();
			coords.setLongitude(lng);
			coords.setLatitude(lat);
			Polygon polygons  = JSON.parseObject(points, Polygon.class);
			
			String path="";
			 for(Point p:polygons.getPath()){
				 path= path + p.getLat()+"_"+p.getLng()+",";
			 }
			 path=path.substring(0, path.length()-1);
			result = DistanceUtil.isInPolygon(coords, path);

			break;
		}

		return result;
	}

	public static void main(String[] args) {
		 Polygon polygons  = new Polygon();
		polygons.setPath(new Point[]{new Point(40.068084,116.275811),new Point(40.068084,116.338621),});
		String path="";
		 for(Point p:polygons.getPath()){
			 path= path + p.getLat()+"_"+p.getLng()+",";
		 }
		 path= path.substring(0, path.length()-1);
		 
		 System.out.println(path);
		
		String s="{\"path\":[{\"lng\":116.275811,\"lat\":40.068084},{\"lng\":116.338621,\"lat\":40.068084},{\"lng\":116.338621,\"lat\":40.045884},{\"lng\":116.275811,\"lat\":40.045884}]}";
		//String points = "116.304916_40.055107,116.323314_40.063336";// 116.312714,40.05889
		System.out.println(AreaUtil.in(Byte.valueOf("1"), s, "40.060464", "116.359749"));
		 
		String	s2="{\"center\":{\"lng\":116.300946,\"lat\":40.057144},\"radius\":149.304569057084}";
		
		System.out.println(AreaUtil.in(Byte.valueOf("2"), s2, "40.057109", "116.301523"));
		
	}
}

/**
 * 多边形>3
 * @author zhangfx
 *
 */
@Data
class Polygon{
	private Point[] path;
}

/**
 * 圆心
 * @author zhangfx
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
class Point{
	private double lat;
	private double lng;//经度
}
/**
 * 圆形
 * @author zhangfx
 *
 */
@Data
class Circle{
	private double radius;
	private Point center;
}