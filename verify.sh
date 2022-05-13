#!/bin/bash

# verify.sh
# a script that verifies if a program generates the correct output

# ask user to specify which program to verify
echo "Please enter the name of the source file without the file extension:"
echo "(For example, if the name of the source file is 'rain.cpp', enter 'rain'.)"
read program

# initialize a directory called 'input'
cd data
rm -r input
mkdir -v input

# set shell option 'extglob' since pattern matching is required
# to selectively extract input files
shopt -s extglob

# populate 'input' by copying all given input files to 'input' directory
cp -v ${program}/*(*.in) -t input

# initialize a directory called 'output_trusted'
rm -r output_trusted
mkdir -v output_trusted

# populate 'output_trusted' by copying all given output files
cp -v ${program}/*(*.out) -t output_trusted

# variable 'program' holds the name of the program to be verified
# compile the source code pointed to by 'program' into a binary of the same name
cd ../solutions_ATFA
g++ -lm -std=c++17 -o ${program} ${program}.cpp
echo "${program}.cpp compiled into '${program}' binary."

# move binary into 'input' directory then change directory to 'input'
mv -v ${program} ../data/input
cd ../data/input

# execute the binary on all input files
# to generate a corresponding set of output files
for file in *.in
do
	./${program} <"$file"> "${file}.out"
	echo "running '${program}' binary on '$file'..."
done

# use "rename" util (installed with 'sudo apt install rename')
# to change file extension from '.in.out' to '.out'
# the rename util uses a "perl experession" to specifiy files to rename
# the '-v' option is used to print changes to standard output stream
rename -v 's/in.out/out/' *.in.out

# make a directory called 'output' in data directory
rm -r ../output
mkdir -v ../output

# move all generated output files to 'output' directory
for file in *.out
do
	mv -v "$file" ../output
done

# use 'diff' util to recursively compare files
# between 'output' directory and 'output_trusted' directory.
# all differences are printed to standard output stream
cd ..
diff -r output output_trusted
