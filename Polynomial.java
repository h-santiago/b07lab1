public class Polynomial{
    
    double[] coeffs;

    public Polynomial(){
        this.coeffs = new double [1];
    }

    public Polynomial(double[] coeffs){
        this.coeffs = coeffs;
    }

    public Polynomial add(Polynomial p){
        Polynomial add_coef;
    	
    	if(this.coeffs.length > p.coeffs.length){
        	add_coef = new Polynomial(this.coeffs);
        	for(int i = 0; i < this.coeffs.length; i++){
            	if(i > p.coeffs.length - 1){
                    add_coef.coeffs[i] = this.coeffs[i];
                } else {
                    add_coef.coeffs[i] = this.coeffs[i] + p.coeffs[i];
                }
            }
        } else {
        	add_coef = new Polynomial(p.coeffs);
        	for(int i = 0; i < p.coeffs.length; i++){
                if(i > this.coeffs.length - 1){
                    add_coef.coeffs[i] = p.coeffs[i];
                } else {
                    add_coef.coeffs[i] = this.coeffs[i] + p.coeffs[i];
                }
            }
        }
        return add_coef;
        
    }

    public double evaluate(double x){
        double result = 0;
        for(int i = 0; i < this.coeffs.length; i++){
            result += this.coeffs[i] * Math.pow(x, i);
        }
        return result;
    }

    public boolean hasRoot(double x){
        return this.evaluate(x) == 0;
    }

}