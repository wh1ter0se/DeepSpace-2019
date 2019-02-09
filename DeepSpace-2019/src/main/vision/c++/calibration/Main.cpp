#include "Calibration.h"


/**
 * Calibration application for Vision.
 * Written by Brach Knutson
 */

using namespace std;

GtkWidget *window; //main window that displays everything on desktop
GtkWidget *master_container; //container that packs everything into window
GtkWidget *button_container; //container that the buttons go into
GtkWidget *exit_container;
GtkWidget *textbox_container;
GtkWidget *textbox_label;
GtkWidget *contour_information; //label that contour data goes into
GtkWidget *distance_text_box;
GtkWidget *process_button; //button that triggers processing
GtkWidget *resume_button; //button that resumes normal function after processing.
GtkWidget *distance_button;
GtkWidget *exit_button;

cv::VideoCapture cap(0); //create and init the videocapture we shall use
bool resume = true; //does not update frames when false

static gboolean delete_event(GtkWidget *widget, GdkEvent *event, gpointer pointer) {
    return FALSE;
}

static void Destroy() {
    gtk_main_quit();
}

/**
 * processes the image and outputs the targeting information on the gui
 */
static void Process() {
    //try to get text from the textbox and parse to int
    
    if(resume) {
        resume = false;
        string Message = Calibration::Process(cap);
        const gchar *mes; //the text we display on the label
        mes = Message.c_str();
        gtk_label_set_text(GTK_LABEL(contour_information), mes);
    }

}

/**
 * allows the stream to keep going after processing an image.
 */
static void Resume() {
    resume = true;
}

static void tune_distance() {
    cout << "tuning distance.\n";
    cout.flush();
    
    //opens a new window where depth perception can be tuned.
    Tuning tuner = Tuning(cap);
}

/**
 * update the image seen on screen.
 */
static gboolean update() {
    if(resume) {
        Calibration::Update(cap);
        // cout << "update\n";
        // cout.flush();
    }

    return TRUE;
}

int main(int argc, char *argv[]) {

    gtk_init(&argc, &argv);

    //set all the camera settings (so just resolution)
    cap.set(cv::CAP_PROP_FRAME_WIDTH, 252);
    cap.set(cv::CAP_PROP_FRAME_HEIGHT, 128);

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
    
    //exit button box
    exit_container = gtk_vbox_new(FALSE, 0);
    gtk_box_pack_start(GTK_BOX(master_container), exit_container, TRUE, TRUE, 0);
    
    //distance button
    distance_button = gtk_button_new_with_label("Tune Distance");
    gtk_signal_connect(GTK_OBJECT(distance_button), "clicked", G_CALLBACK(tune_distance), NULL);
    gtk_box_pack_start(GTK_BOX(exit_container), distance_button, TRUE, TRUE, 0);
    
    //exit button
    exit_button = gtk_button_new_with_label("Exit");
    gtk_signal_connect(GTK_OBJECT(exit_button), "clicked", G_CALLBACK(Destroy), NULL);
    gtk_box_pack_start(GTK_BOX(exit_container), exit_button, TRUE, TRUE, 0);

    //show all widgets
    gtk_widget_show(resume_button);
    gtk_widget_show(process_button);
    gtk_widget_show(distance_button);
    gtk_widget_show(exit_button);
    gtk_widget_show(exit_container);
    gtk_widget_show(button_container);
    gtk_widget_show(contour_information);
    gtk_widget_show(master_container);
    gtk_widget_show(window);

    g_timeout_add(25, GtkFunction(update), NULL); //calls updateValues() 10 times/second
    gtk_main();
    return 0;
}
