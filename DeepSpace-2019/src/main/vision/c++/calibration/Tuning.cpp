#include "Calibration.h"

/**
 * Source file for the Tuning class, which provides a window with sliders
 * to tune the depth perception of the vision program.
 */
 
gint processID;
cv::VideoCapture vidCap;
GtkWidget *tuning_window; //the main window everything goes in
GtkWidget *tuning_content_pane; //the box that displays all contents of window
GtkWidget *tuning_output_label; //the label that displays the distance between camera and target
GtkWidget *tuning_label_known_distance;
GtkWidget *tuning_slider_known_distance;
GtkWidget *tuning_label_known_width;
GtkWidget *tuning_slider_known_width;
GtkWidget *tuning_label_focal_distance;
GtkWidget *tuning_slider_focal_distance; //the slider where focal distance is adjusted.
GtkWidget *tuning_label_error_correction;
GtkWidget *tuning_slider_error_correction; //the slider where error correction is adjusted.
GtkWidget *tuning_exit_button; //the button that closes the window and disposes the object.

static gboolean tuning_Update() {
	double known_height = 0,
		focal_height = 0,
		error_correct = 0,
		known_distance = 0;
	
	//grab the values off the sliders
	known_height = (double) gtk_range_get_value(GTK_RANGE(tuning_slider_known_width));
	focal_height = (double) gtk_range_get_value(GTK_RANGE(tuning_slider_focal_distance));
	error_correct = (double) gtk_range_get_value(GTK_RANGE(tuning_slider_error_correction));
	known_distance = (double) gtk_range_get_value(GTK_RANGE(tuning_slider_known_distance));

	int dist = PostProcessor::Update(vidCap, known_height, focal_height, error_correct, known_distance);
	string dist_str = std::to_string(dist); //converts our value into a string to put on label

	dist_str = "Distance: " + dist_str;
	const gchar *out = dist_str.c_str(); //make it contant so that we can put it onto the label

	gtk_label_set_text(GTK_LABEL(tuning_output_label), out);

	return TRUE;
}


static gboolean tuning_delete_event(GtkWidget *widget, GdkEvent *event, gpointer pointer) {
    return FALSE;
}

static void tuning_Destroy() {
	gtk_timeout_remove(processID);

	//destroy all the widgets to clear up memory or something
    gtk_widget_destroy(tuning_exit_button);
	gtk_widget_destroy(tuning_slider_error_correction);
	gtk_widget_destroy(tuning_label_error_correction);
	gtk_widget_destroy(tuning_slider_focal_distance);
	gtk_widget_destroy(tuning_label_focal_distance);
	gtk_widget_destroy(tuning_slider_known_distance);
	gtk_widget_destroy(tuning_label_known_distance);
	gtk_widget_destroy(tuning_label_known_width);
	gtk_widget_destroy(tuning_slider_known_width);
	gtk_widget_destroy(tuning_content_pane);
	gtk_widget_destroy(tuning_window);

}



/**
 * Uses the given video stream to create a new instance of the class.
 */
Tuning::Tuning(cv::VideoCapture cap) {
	vidCap = cap;
	
	//create the main window
	tuning_window = gtk_window_new(GTK_WINDOW_TOPLEVEL);
	gtk_signal_connect(GTK_OBJECT(tuning_window), "delete-event", G_CALLBACK(tuning_delete_event), NULL);
	gtk_signal_connect(GTK_OBJECT(tuning_window), "Destroy", G_CALLBACK(tuning_Destroy), NULL);
	
	tuning_content_pane = gtk_vbox_new(FALSE, 0);
	gtk_container_add(GTK_CONTAINER(tuning_window), tuning_content_pane);

	//output label
	tuning_output_label = gtk_label_new("Distance:");
	gtk_box_pack_start(GTK_BOX(tuning_content_pane), tuning_output_label, TRUE, TRUE, 0);

	//sliders

	//known distance label
	tuning_label_known_distance = gtk_label_new("Known distance:");
	gtk_box_pack_start(GTK_BOX(tuning_content_pane), tuning_label_known_distance, TRUE ,FALSE, 0);

	//known distance slider
	tuning_slider_known_distance = gtk_hscale_new_with_range(6, 60, 0.5);
	gtk_object_set(GTK_OBJECT(tuning_slider_known_distance), "width-request", 400, NULL);
	gtk_box_pack_start(GTK_BOX(tuning_content_pane), tuning_slider_known_distance, TRUE, FALSE, 0);

	//known width label
	tuning_label_known_width = gtk_label_new("Known Width:");
	gtk_box_pack_start(GTK_BOX(tuning_content_pane), tuning_label_known_width, TRUE, FALSE, 0);

	//known width slider
	tuning_slider_known_width = gtk_hscale_new_with_range(0.5, 7, 0.1);
	gtk_object_set(GTK_OBJECT(tuning_slider_known_distance), "width-request", 400, NULL);
	gtk_box_pack_start(GTK_BOX(tuning_content_pane), tuning_slider_known_width, TRUE, FALSE, 0);

	//focal label
	tuning_label_focal_distance = gtk_label_new("Focal width:");
	gtk_box_pack_start(GTK_BOX(tuning_content_pane), tuning_label_focal_distance, TRUE, FALSE, 0);

	//focal slider
	tuning_slider_focal_distance = gtk_hscale_new_with_range(0, 500, 1);
	gtk_object_set(GTK_OBJECT(tuning_slider_focal_distance), "width-request", 400, NULL);
	gtk_box_pack_start(GTK_BOX(tuning_content_pane), tuning_slider_focal_distance, TRUE, FALSE, 0);

	//err label
	tuning_label_error_correction = gtk_label_new("Error Correction:");
	gtk_box_pack_start(GTK_BOX(tuning_content_pane), tuning_label_error_correction, TRUE, FALSE, 0);

	//err slider
	tuning_slider_error_correction = gtk_hscale_new_with_range(0, 5, 0.05);
	gtk_object_set(GTK_OBJECT(tuning_slider_error_correction), "width-request", 400, NULL);
	gtk_box_pack_start(GTK_BOX(tuning_content_pane), tuning_slider_error_correction, TRUE, FALSE, 0);

	//exit button
	tuning_exit_button = gtk_button_new_with_label("Exit");
	gtk_signal_connect(GTK_OBJECT(tuning_exit_button), "clicked", G_CALLBACK(tuning_Destroy), NULL);
	gtk_box_pack_start(GTK_BOX(tuning_content_pane), tuning_exit_button, TRUE, TRUE, 0);

	gtk_widget_show(tuning_exit_button);
	gtk_widget_show(tuning_slider_error_correction);
	gtk_widget_show(tuning_label_error_correction);
	gtk_widget_show(tuning_slider_focal_distance);
	gtk_widget_show(tuning_label_focal_distance);
	gtk_widget_show(tuning_slider_known_distance);
	gtk_widget_show(tuning_label_known_distance);
	gtk_widget_show(tuning_label_known_width);
	gtk_widget_show(tuning_slider_known_width);
	gtk_widget_show(tuning_output_label);
	gtk_widget_show(tuning_content_pane);
	gtk_widget_show(tuning_window);

	processID = gtk_timeout_add(25, GtkFunction(tuning_Update), NULL);

}
