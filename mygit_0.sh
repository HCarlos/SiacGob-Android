echo "# Centro en Línea App, para Android" >> README.md
git init
git add README.md
git commit -m "Inicio"
git remote add origin https://github.com/HCarlos/SiacGob-Android.git
git push -u origin master

git remote set-url origin https://github.com/HCarlos/SiacGob-Android.git
git config --global user.email "r0@tecnointel.mx"
git config --global user.name "HCarlos"
git config --global color.ui true
git config core.fileMode false
git config --global push.default simple

git checkout master

git status

