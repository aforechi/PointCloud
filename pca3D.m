clear all;
close all;

P = readmatrix('./data/armadillo.xyz', 'FileType', 'text', 'Delimiter',' ');
%P = readmatrix('./data/airplane.xyz', 'FileType', 'text', 'Delimiter',' ');
%P = readmatrix('./data/guitar.xyz', 'FileType', 'text', 'Delimiter',' ');
%P = readmatrix('./data/human.xyz', 'FileType', 'text', 'Delimiter',' ');

c = mean(P);
Pc = P-c;

% W rows are the eigenvector sorted by 
% their corresponding eigenvalues in decresing order
[W] = pca(Pc);

% The largest eigenvector aligns along the y-axis,
% The second largest aligns along the x-axis,
% The last eigenvector aligns along the z-axis
R(:,1) = W(:,2);
R(:,2) = W(:,1);
R(:,3) = W(:,3);

% uncomment to flip airplane vertically
% R(:,2) = -R(:,2);

% align the point cloud w.r.t. the eigenvectors
X = (inv(R) * Pc')' + c;

plot3(X(:, 1), X(:, 2), X(:, 3), 'r*')
axis equal
grid on; grid minor; box on;
xlabel('x'); ylabel('y'); zlabel('z')
view(0,89)
title({'PCA highest eigenvalue along y-axis','2nd highest eigenvalue along x-axis'})

writematrix(X,'./data/armadillo_up.xyz', 'FileType', 'text', 'Delimiter',' ');
%writematrix(X,'./data/airplane_up.xyz', 'FileType', 'text', 'Delimiter',' ');
%writematrix(X,'./data/guitar_up.xyz', 'FileType', 'text', 'Delimiter',' ');
%writematrix(X,'./data/human_up.xyz', 'FileType', 'text', 'Delimiter',' ');