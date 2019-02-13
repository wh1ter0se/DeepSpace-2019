#include "Vision.h"

/**
 * Source file for PairData Class.
 * Written by Brach Knutson
 */

using namespace cv;

PairData::PairData(cv::RotatedRect rect1, cv::RotatedRect rect2) {
    this->rect1 = rect1;
    this->rect2 = rect2;
}

cv::Point PairData::center() {
    int x = rect1.center.x + rect2.center.x;
    int y = rect1.center.y + rect2.center.y;
    x /= 2;
    y /= 2;

    return cv::Point(x, y);

}

int PairData::area() {
    int area1 = (int) (rect1.size.width * rect1.size.height);
    int area2 = (int) (rect2.size.width * rect2.size.height);
    
    return area1 + area2;
}

/**
 * Returns the height of the target by averaging the height of the two contours.
 */
int PairData::height() {
    int height1 = Util::WhichIsBigger(rect1.size.width, rect1.size.height);
    int height2 = Util::WhichIsBigger(rect2.size.width, rect2.size.height);
    
    int avg = height1 + height2;
    avg /= 2;
    return avg;
}
