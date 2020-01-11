# Arch Linux - Instructions

This project was originally developed from an issue on CentOS, but at home I use Arch Linux, which required more effort 
than expected to get it working. 

## Java

Arch Linux packages are stripped of debug symbols by default. This only causes issues with `jmap -histo` so the 
following instructions aren't necessary, but I've recorded my steps here for future me, if nobody else.

To  start, this has only been tested with Open JDK 8, which at the time of writing was the 
[java8-openjdk](https://www.archlinux.org/packages/extra/x86_64/jdk8-openjdk/) package.

To get an installed JDK with debug symbols on Arch you have you use the 
[Arch Build System](https://wiki.archlinux.org/index.php/Arch_Build_System).

```bash
mkdir ~/abs
cd ~/abs

svn checkout --depth=empty svn://svn.archlinux.org/packages
svn update java8-openjdk

cd trunk

## Edit PKGBUILD and add:
# groups=('modified')
# options=('!strip')

# Make and install
makepkg -sic

# Confirm it worked, should see 'not stripped' in the output of the following command 
file file /usr/lib/jvm/java-8-openjdk/jre/bin/java
``` 

Note: If you haven't already, you should add `IgnoreGroup = modified` to `/etc/pacman.conf` to stop pacman updating this 
package. Instead you should svn update the abs packages you have manually.

## JVisualVM

JVisualVM is not part of the Java package. You need to install it separately and the 
[visualvm](https://www.archlinux.org/packages/extra/x86_64/visualvm/) package can be installed and run using `visualvm` 

## Jemalloc

I went with the [AUR git version of the Jemalloc](https://aur.archlinux.org/packages/jemalloc-git/) package to make sure
I had the latest version, but the following could probably be done to the ABS version too.

```bash
mkdir ~/aur
cd ~/aur
git clone https://aur.archlinux.org/jemalloc-git.git

cd jemalloc-git

## Edit PKGBUILD and add the following to the autogen.sh command in the build function:
# --enable-prof

# Make and install
makepkg -sic

# Verify it worked, should see '--enable-prof' in the output of the following command
jemalloc-config --config
```
