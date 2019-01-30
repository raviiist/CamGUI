#sat=c2e
#camdir="/moxscnfs/oa/log/64/schemacs/C2E/cam/"
prodir="/moxscnfs/oa/operations/c2eops/IdeaProjects/CamGUI/"
comdir="/moxscnfs/oa/log/64/schemacs/common/"
camfile="CamLog.C2E.mx2c2ertp1.txt"
dt=`date +%j`
dtp=`date -d "1 day ago" +%j`
hd=1
noOfSat=`grep "/cam/" $prodir/camconfig.inp |wc -l`
while (( $hd <= $noOfSat )) ; do
sat=`grep "/cam/" $prodir/camconfig.inp |head -$hd |tail -1 |awk '{print$1}'`
satcode=`echo $sat |tr '[:lower:]' '[:upper:]'`
camdir=`grep "/cam/" $prodir/camconfig.inp |head -$hd |tail -1 |awk '{print$2}'`
tail -100000 $camdir/CamLog."$satcode".mx2"$sat"rtp1.txt > partialCamLog"$satcode"rtp1n2.txt
tail -100000 $camdir/CamLog."$satcode".mx2"$sat"rtp2.txt >> partialCamLog"$satcode"rtp1n2.txt
tail -100000 $camdir/CamLog."$satcode".mx2"$sat"rtp3.txt >> partialCamLog"$satcode"rtp1n2.txt
#tail -100000 /moxscnfs/oa/log/64/schemacs/"$satcode"/cam/CamLog."$satcode".mx2"$sat"rtp1.txt > partialCamLog"$satcode"rtp1n2.txt
#tail -100000 /moxscnfs/oa/log/64/schemacs/"$satcode"/cam/CamLog."$satcode".mx2"$sat"rtp2.txt >> partialCamLog"$satcode"rtp1n2.txt
sort -u partialCamLog"$satcode"rtp1n2.txt > partialCamLog"$satcode"rtpBOTH.txt
#tail -100000 /moxscnfs/oa/log/64/schemacs/C2E/cam/CamLog.C2E.mx2c2ertp1.txt > partialCamLogC2Ertp1.txt
#cp partialCamLogC2Ertp1.txt $camdir/partialCamLogC2Ertp1.txt
grep "Message:"$sat"" partialCamLog"$satcode"rtpBOTH.txt |grep -e " $dt" -e " $dtp" |awk '{$1=$2=$3=$4=$6=$7=$8=$9=""; $0=$0;} NF=NF' |sort -u |tail -100 |grep -e "$dt" -e "$dtp" |tail -70 > logtest.$sat
#cp logtest $prodir/
cat logtest.$sat |awk '{$1=$2=""; $0=$0;} NF=NF' |sort -u > logtest_sorted.$sat
echo "Printing fot $sat ..."
cp $prodir/camProg.out $comdir/camProg.log
hd=`expr $hd + 1`
done
