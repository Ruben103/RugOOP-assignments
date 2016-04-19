public class Complex {
	
	private double real_part;
	private double imaginary_part;
	
	public Complex(){
		this.real_part = 0;
		this.imaginary_part = 0;
	}
	
	public Complex(double real_part){
		this.real_part = real_part;
		this.imaginary_part = 0;
	}
	
	public Complex(double real_part, double imaginary_part){
		this.real_part = real_part;
		this.imaginary_part = imaginary_part;
	}

	public double getReal_part() {
		return real_part;
	}

	public void setReal_part(double real_part) {
		this.real_part = real_part;
	}

	public double getImaginary_part() {
		return imaginary_part;
	}

	public void setImaginary_part(double imaginary_part) {
		this.imaginary_part = imaginary_part;
	}
	
	public String toString(){
		return this.getReal_part() + " " + this.getImaginary_part() + "i";	
	}

	public void add(Complex complex_addend){
		this.real_part += complex_addend.getReal_part();
		this.imaginary_part += complex_addend.getImaginary_part();
	}
	
	public void add(double real_addend){
		this.add(new Complex(real_addend, 0));
	}
	
	public void multiply(Complex complex_addend){
		double tmp_real_part = this.getReal_part();
		
		this.real_part = this.getReal_part()*complex_addend.getReal_part() 
							- this.getImaginary_part()*complex_addend.getImaginary_part();
		
		this.imaginary_part = tmp_real_part*complex_addend.getImaginary_part() 
								+ this.getImaginary_part()*complex_addend.getReal_part();
	}
	
	public void multiply(double real_factor){
		this.multiply(new Complex(real_factor, 0));
	}
	
	public Complex square(){
		return new Complex(Math.pow(this.getReal_part(), 2) - Math.pow(this.getImaginary_part(), 2),
							2.0f*this.getReal_part()*this.getImaginary_part());
	}
	
	public double norm(){
		return Math.sqrt(Math.pow(this.getReal_part(), 2) + Math.pow(this.getImaginary_part(), 2));
	}
}
