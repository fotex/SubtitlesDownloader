# SubtitlesDownloader
SubtitlesDownloader is a free and opensource application to download subtitles for movies and tvshows.

## Download
Download latest release [here](https://github.com/fotex/SubtitlesDownloader/releases)


## Releases
```
Release alpha_1.1.0
- Changed OSApi to REST
- Changed design and functionality of the subtitle shifter module.
  Now user can fit subtitles to movie easly. Movie and subtitle file is required to shift subtitles.
Release alpha_1.0.8
- Added option to search extended version of subtitles.
- Bug fixes.
Release alpha_1.0.7
- Added subtitle block support.
- Added Drag & Drop support
- Added logging support to OpenSubtitles
- Added subtitles offset support (only srt extension)
```

## Features 
- Drag and Drop a movie file to automatic search subtitle,
- SRT subtitles shift support,
- Block subtitle with translation mistakes and search new one,
- OpenSubtitles logging support,
- Clean and intuitive GUI.

## Linux Installation
Download the latest jar version from releases. <br>
Type the following commands:
```
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java8-installer

sudo update-alternatives --config java
```
Select JDK8.

If you see SSL error then you should update the cacerts keystore file.

```
cp -i cacerts /etc/ssl/certs/java/cacerts 
```
Run application
```
java -jar SubtitleDownloader.jar
```

## Movie names

The subtitles search is based on file names. In future a movie hash searching will be added.<br>
e.g.
```
Test.tvshow.title.S01E05.1080p.NF.WEBRip.x265.HEVC.6CH-MRN
```
is equal to: <br>
<b>Name:</b> Test tvshow title <br>
<b>Season:</b> 01 <br>
<b>Episode:</b> 05 <br>
<b>is TV Show?:</b> Yes <br>
<br>
Title converter accept only this patterns: 
```
[moviename][resolution e.g. 1080p]
```
or
```
[tvshowname][S##E## e.g. S01E02]
```
e.g. "S01E01.Westworld" is not acceptable.<br><br>

## GUI

GUI is not responsive yet.

![Blockedsubtitles Image](https://i.imgur.com/pdCJftD.png)

![Blockedsubtitles Image](https://i.imgur.com/SGlbVCV.png)

![Blockedsubtitles Image](https://i.imgur.com/tDStJh1.png)

![Blockedsubtitles Image](https://i.imgur.com/evRETxT.png)

![Blockedsubtitles Image](https://i.imgur.com/N8xQPqp.png)

## Built with

* [OpenSubtitles](https://www.opensubtitles.org/) - Subtitles database
* [TheMovieDB](https://www.themoviedb.org/) - Movies database
* [FontAwesome](https://fontawesome.com) - Icons database
* [Maven](https://maven.com/) - Maven

## Issues
If you found a bug in application, please create issue on github.

## Author and Contact

* **Grzegorz Woźnicki** - [fotex](https://github.com/fotex) <br>
Email: <b>gwoznicki96@gmail.com</b>

## License

This project is licensed under the terms of the MIT license.
