#include "Calibration.h"

/**
 * Source file for the Tuning class, which provides a window with sliders
 * to tune the depth perception of the vision program.
 */
 

/**
 * Uses the given video stream to create a new instance of the class.
 */
Tuning::Tuning(cv::VideoCapture cap) {
	this->cap = cap;
	
	//create the main window
	this->window = gtk_window_new(GTK_WINDOW_TOPLEVEL);
	gtk_signal_connect(window, "delete-event", G_CALLBACK(delete_event), NULL);
	gtk_signal_connect(window, "Destroy", G_CALLBACK(Destroy), NULL);
	
}

static void Tuning::Update() {
	cout << "tune update";
	cout.flush();
}


static gboolean Tuning::delete_event(GtkWidget *widget, GdkEvent *event, gpointer pointer) {
    return FALSE;
}

static void Tuning::Destroy() {
    gtk_widget_destroy(window);
}
