clear all;
close all;

P = readmatrix('./data/armadillo.xyz', 'FileType', 'text', 'Delimiter',' ');
%P = readmatrix('./data/airplane.xyz', 'FileType', 'text', 'Delimiter',' ');
%P = readmatrix('./data/guitar.xyz', 'FileType', 'text', 'Delimiter',' ');
%P = readmatrix('./data/human.xyz', 'FileType', 'text', 'Delimiter',' ');

c = mean(P);
Pc = P-c;
C = cov(Pc);

% V rows are the right eigenvector sorted by 
% their corresponding eigenvalues D
[V, D] = eig(C);
d = sqrt(diag(D));

% shift left the eigenvectors so as the largest eigenvector aligns along the y axis
V = V(:,[2 3 1]);
d = d([2 3 1]);

% flip vertically (except for guitar)
V(:,2) = -V(:,2);

e1 = V(:,1);
e2 = V(:,2);
e3 = V(:,3);

% align the point cloud w.r.t. the eigenvectors
Vi = inv(V);
X = (Vi * Pc')' + c;

e1 = Vi * e1;
e2 = Vi * e2;
e3 = Vi * e3;

plot3(X(:, 1), X(:, 2), X(:, 3), 'r*')
axis equal
grid on; grid minor; box on;
xlabel('x'); ylabel('y'); zlabel('z')
view(0,89)
title({'PCA highest eigenvalue along y-axis','2nd highest eigenvalue along x-axis'})
hold on
quiver3(c(1),c(2),c(3),e3(1),e3(2),e3(3),d(3),'b','LineWidth',5);
quiver3(c(1),c(2),c(3),e2(1),e2(2),e2(3),d(2),'g','LineWidth',5);
quiver3(c(1),c(2),c(3),e1(1),e1(2),e1(3),d(1),'r','LineWidth',5);
hold off;

writematrix(X,'./data/armadillo_up.xyz', 'FileType', 'text', 'Delimiter',' ');
%writematrix(X,'./data/airplane_up.xyz', 'FileType', 'text', 'Delimiter',' ');
%writematrix(X,'./data/guitar_up.xyz', 'FileType', 'text', 'Delimiter',' ');
%writematrix(X,'./data/human_up.xyz', 'FileType', 'text', 'Delimiter',' ');