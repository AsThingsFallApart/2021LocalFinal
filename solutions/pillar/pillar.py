# Arup Guha
# 9/8/2021
# Alternate Solution to 2021 UCF Locals Problem: Caterpillar Walk

# of buildings
n = int(input())

# height[i] stores height of the building from x=i to x=i+1.
height = [0]*101

# Go through each building
for i in range(n):

    # Get input.
    toks = [int(x) for x in input().split()]

    # Since building is small, just set each unit height.
    for j in range(toks[0], toks[0]+toks[1]):
        height[j] = toks[2]

# Horizontal walking.    
res = 100

# Looking for any vertical segments.
for i in range(100):
    res += abs(height[i]-height[i+1])

# Ta da!
print(res)
