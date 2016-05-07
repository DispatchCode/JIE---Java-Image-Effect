class MotionBlurLeftFilter extends MotionBlurFilter implements ImageFilter {
  MotionBlurLeftFilter(int filter) {
    switch(filter) {
      case FILTER_3X3:
        setKernel(new int[][] {{1,0,0},{0,1,0},{0,0,1}});
        break;
      case FILTER_5X5:
        setKernel(new int[][] {{1,0,0,0,0},{0,1,0,0,0},{0,0,1,0,0},{0,0,0,1,0},{0,0,0,0,1}});
      break;
      case FILTER_7X7:
        setKernel(new int[][] {{1,0,0,0,0,0,0},{0,1,0,0,0,0,0},{0,0,1,0,0,0,0},{0,0,0,1,0,0,0},{0,0,0,0,1,0,0},{0,0,0,0,0,1,0},{0,0,0,0,0,0,1}});
      break;
      case FILTER_9X9:
        setKernel(new int[][] {{1,0,0,0,0,0,0,0,0},{0,1,0,0,0,0,0,0,0},{0,0,1,0,0,0,0,0,0},{0,0,0,1,0,0,0,0,0},{0,0,0,0,1,0,0,0,0},{0,0,0,0,0,1,0,0,0},{0,0,0,0,0,0,1,0,0},{0,0,0,0,0,0,0,1,0},{0,0,0,0,0,0,0,0,1}});
    }  
  }
  
  MotionBlurLeftFilter() {
    super();
  }
  
  public void setKernel(int[][] kernel) {
    super.setKernel(kernel);
  }
  
  public RawRGBImage filter(RawRGBImage rawImage) {
    return super.filter(rawImage);
  }
}