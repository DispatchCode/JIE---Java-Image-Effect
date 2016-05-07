abstract class MotionBlurFilter extends BlurFilter implements ImageFilter {
  
  public static final int FILTER_3X3 = 1;
  public static final int FILTER_5X5 = 2;
  public static final int FILTER_7X7 = 3;
  public static final int FILTER_9X9 = 4;
  
  MotionBlurFilter() {
    super();
  }
  
  @Override
  public void setKernel(int[][] kernel) {
    super.setKernel(kernel);
  }
  
  public RawRGBImage filter(RawRGBImage rawImage) {
    return super.filter(rawImage);
  }
}