import java.awt.Color;

public class Mandelbrot {
	
	public int repetation(Complex c, int max){
		Complex d = new Complex();
		int iteration = 0;
		
		for (; d.norm() <= 2.0f && iteration < max ; iteration++){
			Complex sum = new Complex();
			sum.add(d.square());
			sum.add(c);
			d = sum;
		}

		return iteration;	
	}
	
	public Color colorAt(double x, double y){
		Complex c = new Complex(x,y);
		int iterations = repetation(c, 50) % 255;
		
		return new Color(iterations, iterations, iterations);
	}

}


