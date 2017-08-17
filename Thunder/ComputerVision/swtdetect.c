#include "ccv.h"
#include "swtdetect.h"

ccv_array_t* swt(char* file){
    ccv_dense_matrix_t *image = 0;
    ccv_read(file, &image, CCV_IO_GRAY | CCV_IO_ANY_FILE);
    ccv_enable_default_cache();
	if (image != 0) {
		ccv_array_t* words = ccv_swt_detect_words(image, ccv_swt_default_params);
		ccv_matrix_free(image);
        ccv_drain_cache();
        return words;
	}
}