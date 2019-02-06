#include <iostream>
#include "gtk-2.0/gtk/gtk.h"
#include "Calibration.h"


/**
 * Calibration application for Vision.
 * Written by Brach Knutson
 */

using namespace std;

GtkWidget *window; //main window that displays everything on desktop
GtkWidget *master_container; //container that packs everything into window
GtkWidget *button_container; //container that the buttons go into
GtkWidget *contour_information; //label that contour data goes into
GtkWidget *process_button; //button that triggers processing
GtkWidget *resume_button; //button that resumes normal function after processing.

cv::VideoCapture cap(0); //create and init the videocapture we shall use
bool resume = true; //does not update frames when false

static gboolean delete_event(GtkWidget *widget, GdkEvent *event, gpointer pointer) {
    return FALSE;
}

static void Destroy() {
    gtk_main_quit();
}

static void Process() {
    if(resume)
        Calibration::Process(cap);
        resume = false;
}

static void Resume() {
    resume = true;
}

static void update() {
    cout << resume << "\n";
    if(resume)
        Calibration::Update(cap);
}

int main(int argc, char *argv[]) {

    gtk_init(&argc, &argv);

    //main window
    window = gtk_window_new(GTK_WINDOW_TOPLEVEL); //create new window
    g_signal_connect(window, "delete-event", G_CALLBACK(delete_event), NULL);
    g_signal_connect(window, "destroy", G_CALLBACK(Destroy), NULL);
    gtk_container_set_border_width(GTK_CONTAINER(window), 50);

    //master window
    master_container = gtk_vbox_new(FALSE, 10);
    gtk_container_add(GTK_CONTAINER(window), master_container);

    //contour information
    contour_information = gtk_label_new("Contour information will show up here when you process an image.");
    gtk_box_pack_start(GTK_BOX(master_container), contour_information, TRUE, TRUE, 10);

    
    //button box 
    button_container = gtk_hbox_new(FALSE, 10);
    gtk_box_pack_start(GTK_BOX(master_container), button_container, TRUE, TRUE, 10);

    //process button
    process_button = gtk_button_new_with_label("Process Image");
    gtk_signal_connect(GTK_OBJECT(process_button), "clicked", G_CALLBACK(Process), NULL);
    gtk_box_pack_start(GTK_BOX(button_container), process_button, TRUE, TRUE, 0);

    //resume button
    resume_button = gtk_button_new_with_label("Resume");
    gtk_signal_connect(GTK_OBJECT(resume_button), "clicked", G_CALLBACK(Resume), NULL);
    gtk_box_pack_start(GTK_BOX(button_container), resume_button, TRUE, TRUE, 0);

    //show all widgets
    gtk_widget_show(resume_button);
    gtk_widget_show(process_button);
    gtk_widget_show(button_container);
    gtk_widget_show(contour_information);
    gtk_widget_show(master_container);
    gtk_widget_show(window);

    g_timeout_add(100, GtkFunction(update), NULL); //calls updateValues() 10 times/second
    gtk_main();
    return 0;
}