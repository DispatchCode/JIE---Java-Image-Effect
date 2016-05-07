class MotionBlurRightFilter extends MotionBlurFilter implements ImageFilter {
  MotionBlurRightFilter(int filter) {
    switch(filter) {
      case FILTER_3X3:
        setKernel(new int[][] {{0,0,1},{0,1,0},{1,0,0}});
        break;
      case FILTER_5X5:
        setKernel(new int[][] {{0,0,0,0,1},{0,0,0,1,0},{0,0,1,0,0},{0,1,0,0,0},{1,0,0,0,0}});
      break;
      case FILTER_7X7:
        setKernel(new int[][] {{0,0,0,0,0,0,1},{0,0,0,0,0,1,0},{0,0,0,0,1,0,0},{0,0,0,1,0,0,0},{0,0,1,0,0,0,0},{0,1,0,0,0,0,0},{1,0,0,0,0,0,0}});
      break;
      case FILTER_9X9:
        setKernel(new int[][] {{0,0,0,0,0,0,0,0,1},{0,0,0,0,0,0,0,1,0},{0,0,0,0,0,0,1,0,0},{0,0,0,0,0,1,0,0,0},{0,0,0,0,1,0,0,0,0},{0,0,0,1,0,0,0,0,0},{0,0,1,0,0,0,0,0,0},{0,1,0,0,0,0,0,0,0},{1,0,0,0,0,0,0,0,0}});
    }  
  }
  
  MotionBlurRightFilter() {
    super();
  }
  
  public void setKernel(int[][] kernel) 
  {
    super.setKernel(kernel);  
  }
  
  public RawRGBImage filter(RawRGBImage rawImage) {
    return super.filter(rawImage);
  }
}