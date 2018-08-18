#include "stuff.h"
#include <stdio.h>
#include <stdlib.h>

unsigned int allocation_amount = 0.5 * 1024 * 1024;

void do_stuff_non_leaky(char* string)
{
    char* str = (char*) malloc(allocation_amount);
    memset(str, 0xff, allocation_amount);
    strcpy(str, string);
    printf("String = %s, Address = %p\n", str, (void *) str);
    fflush(stdout);
    free(str);
}

void do_stuff_leaky(char* string)
{
    char* str = (char*) malloc(allocation_amount);
    memset(str, 0xff, allocation_amount);
    strcpy(str, string);
    printf("String = %s, Address = %p\n", str, (void *) str);
    fflush(stdout);

    // Whoops!
//    free(str);
}