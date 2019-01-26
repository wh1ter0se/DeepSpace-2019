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