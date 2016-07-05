git remote set-url origin https://github.com/HCarlos/CentroEnLinea_Android.git
git config --global user.name "HCarlos"
git config --global color.ui true
git config core.fileMode false
git config --global push.default simple

git checkout master

git status

git add .

git commit -m "CentroEnLínea Update 01"

git push -u origin master

exit
