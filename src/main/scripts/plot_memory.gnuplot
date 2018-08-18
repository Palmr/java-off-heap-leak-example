#!/usr/bin/gnuplot-wx

# log line example: 2018-07-11_10:09:05 337056 kB
set xdata time
set timefmt "%Y-%m-%d_H:%M:%S"
set format x "%H:%M"
set timefmt "%Y-%m-%d_%H:%M:%S"

set title "Resident set use"
set xlabel "time"
set ylabel "RSS (MB)"

set style line 101 lt 0 lw 1

set grid mytics
set grid xtics ytics ls 101,
set ytics 50
set mytics 2
show grid

set term wxt 0 title "Resident set use"
files=system('ls -1B memory.log')
plot for [f in files] f using 1:($2/1024) with lines title f noenhanced

pause -1
