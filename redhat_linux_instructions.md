# Redhat/CentOS/Fedora Linux - Instructions

This project was originally developed from an issue on CentOS, and then tested on Fedora.
Java on these distributions works fine with all the commands needed. But the standard Jemalloc package does not have
profiling enabled.

## Jemalloc

The following is a bash script to do this if on a Linux distribution using dnf:

```bash
#!/bin/bash

# download source rpm to this folder
sudo dnf download --source jemalloc

# install source folder, will go to user home/rpmbuilds
rpm -ivh jemalloc*.src.rpm

# go to specs
cd ~/rpmbuild/SPECS

# get deps
sudo dnf builddep jemalloc.spec

# add --enable-prof to configure argument
sed -i '/^%configure/ s/$/ --enable-prof/' ~/rpmbuild/SPECS/jemalloc.spec

# build package
rpmbuild -bb jemalloc.spec

# install resultant rpm
sudo rpm -ivh `ls -1B ~/rpmbuild/RPMS/x86_64/jemalloc-*.rpm | grep -v 'dev\|debug'`
```
