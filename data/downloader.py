import os

for i in range(0, 78):
    i_fomatted = f'{i:02d}'
    i = str(i)
    os.system("wget https://placedata.reddit.com/data/canvas-history/2022_place_canvas_history-0000000000" + i_fomatted + ".csv.gzip")
    os.system("mv 2022_place_canvas_history-0000000000" + i_fomatted + ".csv.gzip data_" + i + ".csv.gz")
    os.system("gunzip data_" + i + ".csv.gz")