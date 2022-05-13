#!/bin/bash

# judge.sh
# compile a source file, run its binary on all input files, then print "Correct"
# if all generated output matches trusted output

# an aside for aspiring programmers:
# judge.sh is the same as verify.sh execept with all output from shell commands silenced.
# the only output is if the algorithm is correct or not.
# this is identical to how UCF judges judge programs;
# therefore, the problem-solving process with this script mirrors being in
# a competition environment more accurately.
# in a competition, the program's data set will not be viewable
# therefore, "reverse-engineering" an algorithm by analyzing how
# specific inputs map to specific outputs is not a viable problem-solving process.
# to rely on this reverse-engineering problem-solving methodology is to cripple oneself
# for problems that do not have trusted output or even known solutions.
# the BEST problem-solving methodology is to break a problem down via first principles
# then connect pieces together to construct a fundamental solution.
# to find fundamental algorithms faster, not only should programming problems be done
# frequently and intensely, but also analysis on patterns in problems, classes of problems,
# and useful libraries should be reflected on.


# ask user to specify which program to verify
echo "Please enter the name of the source file without the file extension:"
echo "(For example, if the name of the source file is 'rain.cpp', enter 'rain'.)"
read program

# initialize a directory called 'input'
cd data
rm -r input
mkdir input

# set shell option 'extglob' since pattern matching is required
# to selectively extract input files
shopt -s extglob

# populate 'input' by copying all given input files to 'input' directory
cp ${program}/*(*.in) -t input

# initialize a directory called 'output_trusted'
rm -r output_trusted
mkdir output_trusted

# populate 'output_trusted' by copying all given output files
cp ${program}/*(*.out) -t output_trusted

# variable 'program' holds the name of the program to be verified
# compile the source code pointed to by 'program' into a binary of the same name
cd ../solutions_ATFA
g++ -lm -std=c++17 -o ${program} ${program}.cpp

# move binary into 'input' directory then change directory to 'input'
mv ${program} ../data/input
cd ../data/input

# execute the binary on all input files
# to generate a corresponding set of output files
for file in *.in
do
	./${program} <"$file"> "${file}.out"
done

# use "rename" util (installed with 'sudo apt install rename')
# to change file extension from '.in.out' to '.out'
# the rename util uses a "perl experession" to specifiy files to rename
# the '-v' option is used to print changes to standard output stream
rename 's/in.out/out/' *.in.out

# make a directory called 'output' in data directory
# currently in '/data/input' directory...
rm -r ../output
mkdir ../output

# move all generated output files to 'output' directory
for file in *.out
do
	mv "$file" ../output
done

# by now, all the output is generated and organized into two directories
# go to generated output directory 'output'
cd ../output

# create a local variable called 'WRONG' that is set if
# some generated output does not match trusted output
WRONG=0

# for each file in 'output' directory, compare with corresponding file in
# 'output_trusted' directory using shell 'cmp' command
for file in *.out
do
	cmp -s "$file" ../output_trusted/"$file"

	# 'cmp' command will return an exit status of 1 if files are not identical
	# use this behavior to set a flag that is used later
	# TODO: print and exit on first mismatch to save time
	if [ $? -ne 0 ] ; then
		WRONG=1
	fi
done

# print 'Correct' if all generated output matches
# print 'Wrong Answer' if some generated output does not match
if [ $WRONG -eq 1 ] ; then
	echo "Wrong Answer"
else
	echo "Correct"
fi

